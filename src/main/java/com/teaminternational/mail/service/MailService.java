package com.teaminternational.mail.service;

import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import com.teaminternational.mail.domain.User;

import javax.mail.MessagingException;
import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */
public interface MailService {
    void sendEmail();

    void receiveEmail();

    List<Message> getDeliveryList(User user);

    void addMessageToDelivery(Message message);

    void setMessageSent(Message message);

    List<Message> getMessages(User user, Folder folder);

    List<Message> getInboxImap(User user) throws MessagingException;

    List<Message> getInboxPop3(User user) throws MessagingException;

}
