package com.teaminternational.mail.service;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3SSLStore;
import com.teaminternational.mail.dao.MessageDAO;
import com.teaminternational.mail.dao.UserDAO;
import com.teaminternational.mail.domain.*;
import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.*;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    @Qualifier("MyPropertiesHolder")
    private PropertyHolder holder;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    @Qualifier("sessionRegistry")
    private SessionRegistry sessionRegistry;

    private static final Logger logger = Logger.getLogger(MailServiceImpl.class);

    /**
     * Gets outbox for user
     *
     * @param user needed user
     * @return list of outbox messages for given user
     */
    @Transactional
    public List<Message> getDeliveryList(User user) {
        return messageDAO.getMessages(user, new Folder(2, "OUTBOX"));
    }

    /**
     * Scheduled task which delivers messages from deliveryList to User's email box. Starts every 30 seconds.
     * If the email address of receiver is not correct it will catch this and make "Delivery failed message"
     * which will be delivered to sender (like in gmail).
     * <p/>
     * If there are a few similar addresses one copy of a message will be delivered only once.
     * <p/>
     * If connection to smtp server is broken it will show exception message and messages will still be in
     * deliveryList, after connection appears messages will be delivered to Users.
     */
    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void sendEmail() {
        logger.info("Scheduled sending started");
        Message currentMessage = new Message();
        List<Message> deliveryList = messageDAO.getMessagesForDelivery();
        ListIterator<Message> messageListIterator = deliveryList.listIterator();
        logger.info("Messages to send : " + deliveryList.size());
        try {
            if (messageListIterator.hasNext()) {
                currentMessage = messageListIterator.next();
                mailSender.setUsername(currentMessage.getUser().getEmail());
                mailSender.setPassword(currentMessage.getUser().getPassword());
                SimpleMailMessage email = new SimpleMailMessage();
                email.setFrom(currentMessage.getSender());
                Set<String> receivers = new HashSet<String>(Arrays.asList(currentMessage.getReceivers().split(", |,|' '| ,")));
                for (String receiver : receivers) {
                    email.setTo(receiver);
                    email.setSubject(currentMessage.getSubject());
                    email.setText(currentMessage.getText());
                    mailSender.send(email);
                }
                if (currentMessage.getSender().equals("webmail.mailer.daemon@gmail.com")) {

                    //Deleting message which must be send from mailer daemon

                    messageDAO.deleteMessage(currentMessage);
                } else {
                    setMessageSent(currentMessage);
                }
                deliveryList.remove(currentMessage);

            }
        } catch (MailParseException mpe) {

            //creating "Failed delivery" message

            User daemon = userDAO.getUser(1);
            Message failed = new Message();
            failed.setReceivers(currentMessage.getSender());
            failed.setSender(daemon.getEmail());
            failed.setSubject("Mail delivery failed");
            failed.setText("Delivery failed due to " + mpe.getMessage() + " in " + currentMessage.getReceivers());
            failed.setUser(daemon);
            addMessageToDelivery(failed);
            deliveryList.remove(currentMessage);
            messageDAO.deleteMessage(currentMessage);
        } catch (MailSendException mse) {
            logger.error("Couldn't send message due to " + mse.getMessage());
        }
        logger.info("Scheduled sending finished");
    }

    /**
     * Scheduled message receiver for all current users. First try to load with imap protocol if server don't support
     * it loads with pop3 protocol. If connection with server is broken shows message.
     */
    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void receiveEmail() {
        logger.info("Scheduled receiving started");
        int allIncomingMessages = 0;
        List<Object> principals = sessionRegistry.getAllPrincipals();
        List<User> currentUsers = new ArrayList<User>();
        for (Object obj : principals) {
            currentUsers.add(userDAO.getUser(((UserDetails) obj).getUsername()));
        }
        logger.info("Current users : " + currentUsers.size());
        for (User user : currentUsers) {
            List<Message> incomingMessages = null;
            try {
                if (holder.getImapHost().equals("") || holder.getImapHost() == null || holder.getImapMailStoreProtocol()
                        .equals("") || holder.getImapMailStoreProtocol() == null) {
                    incomingMessages = getInboxImap(user);
                    allIncomingMessages += incomingMessages.size();
                } else {
                    try {
                        incomingMessages = getInboxPop3(user);
                        allIncomingMessages += incomingMessages.size();
                    } catch (MessagingException me) {
                        logger.error("Error receiving with pop3 due to : " + me.getMessage() + " with user " +
                                user.getEmail());
                    }
                }
            } catch (MessagingException me) {
                logger.error("Error receiving with imap due to : " + me.getMessage() + " with user " +
                        user.getEmail());
            } finally {
                if (incomingMessages != null) {
                    messageDAO.saveIncomingMessages(incomingMessages, user);
                }
            }
        }
        logger.info("Messages retrieved : " + allIncomingMessages);
        logger.info("Scheduled receiving finished");
    }


    /**
     * Adding message to deliveryList which is used by Scheduled task as messages source
     *
     * @param message actually, message to deliver
     */
    @Override
    @Transactional
    public void addMessageToDelivery(Message message) {
        message.setFolder(new Folder(2, "OUTBOX"));
        message.setDate(new Date());
        messageDAO.saveMessage(message);
    }

    /**
     * Saving a successfully send email to the database
     *
     * @param message actually, message to save
     */
    @Override
    @Transactional
    public void setMessageSent(Message message) {
        message.setDate(new Date());
        message.setFolder(new Folder(3, "SENT"));
        messageDAO.updateMessage(message);
    }

    /**
     * Gets the List of Messages by user and folder (1 - INBOX, 2 - OUTBOX, 3 - SENT)
     *
     * @param user user which called the sent messages page
     * @return list of messages which current user send before
     */
    @Transactional
    public List<Message> getMessages(User user, Folder folder) {
        return messageDAO.getMessages(user, folder);
    }

    /**
     * Gets inbox from smtp server with imap protocol
     *
     * @param user who's messages well be loaded
     * @return List of inbox Messages for current user
     * @throws MessagingException if there is no connection to smtp host
     */
    @Transactional
    public List<Message> getInboxImap(final User user) throws MessagingException {
        List<Message> result = new ArrayList<Message>();
        Properties props = new Properties();
        try {
            props.setProperty("mail.store.protocol", holder.getImapMailStoreProtocol());
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore(holder.getImapMailStoreProtocol());
            store.connect(holder.getImapHost(), user.getEmail(), user.getPassword());
            IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX");
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            if (!folder.isOpen()) {
                folder.open(javax.mail.Folder.READ_ONLY);
            }
            javax.mail.Message[] messages = folder.search(ft);

            for (int i = messages.length - 1; i >= 0; i--) {
                Message inboxMessage = new Message();
                inboxMessage.setSender(messages[i].getFrom()[0].toString());
                inboxMessage.setReceivers(messages[i].getAllRecipients()[0].toString());
                inboxMessage.setSubject(messages[i].getSubject());
                inboxMessage.setText(messages[i].getContent().toString());
                inboxMessage.setDate(messages[i].getReceivedDate());
                result.add(inboxMessage);
            }
            folder.close(false);
            store.close();
        } catch (MessagingException e) {
            throw new MessagingException("No connection to host");
        } catch (IOException ioe) {
            throw new MessagingException("No connection, unable to get message content");
        }

        return result;
    }

    /**
     * Gets inbox from smtp server with pop3 protocol
     *
     * @param user who's messages well be loaded
     * @return List of inbox Messages for current user
     * @throws MessagingException if there is no connection to smtp host
     */
    @Transactional
    public List<Message> getInboxPop3(User user) throws MessagingException {
        Properties pop3Props = new Properties();
        List<Message> result = new ArrayList<Message>();
        try {
            pop3Props.setProperty("mail.pop3.socketFactory.class", holder.getPop3SocketFactoryClass());
            pop3Props.setProperty("mail.pop3.socketFactory.port", holder.getPop3SocketFactoryPort());
            pop3Props.setProperty("mail.pop3.port", holder.getPop3Port());
            URLName url = new URLName(holder.getPop3Protocol(), holder.getPop3Host(),
                    Integer.parseInt(holder.getPop3Port()), "", user.getEmail(), user.getPassword());
            Session session = Session.getInstance(pop3Props, null);
            POP3SSLStore store = new POP3SSLStore(session, url);
            store.connect();
            javax.mail.Folder inbox = store.getDefaultFolder();
            inbox = inbox.getFolder("inbox");
            inbox.open(javax.mail.Folder.READ_ONLY);
            javax.mail.Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            FetchProfile fp = new FetchProfile();
            fp.add(FetchProfile.Item.ENVELOPE);
            fp.add(FetchProfile.Item.CONTENT_INFO);
            inbox.fetch(messages, fp);
            for (int i = messages.length - 1; i >= 0; i--) {
                Message inboxMessage = new Message();
                inboxMessage.setSender(messages[i].getFrom()[0].toString());
                inboxMessage.setReceivers(messages[i].getAllRecipients()[0].toString());
                inboxMessage.setSubject(messages[i].getSubject());
                inboxMessage.setText(messages[i].getContent().toString());
                inboxMessage.setDate(messages[i].getSentDate());
                result.add(inboxMessage);
            }
            inbox.close(false);
            store.close();
        } catch (MessagingException e) {
            throw new MessagingException("No connection to host");
        } catch (IOException ioe) {
            throw new MessagingException("No connection, unable to get message content");
        }
        return result;
    }
}
