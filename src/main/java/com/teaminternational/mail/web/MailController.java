package com.teaminternational.mail.web;

import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import com.teaminternational.mail.service.AddressBookService;
import com.teaminternational.mail.service.MailService;
import com.teaminternational.mail.service.PropertyHolder;
import com.teaminternational.mail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 18.10.13
 */

@Controller
@RequestMapping(value = "/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;


    @Autowired
    private AddressBookService addressBookService;

    /**
     * Gets principal from spring security context for current request
     * @return name of user that sends the request
     */
    private String getCurrentUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Loads inbox page. If there is no value of imap host in property file it will choose pop3 to load inbox
     * messages if connections in broken it will show user-friendly Oops page.
     *
     * @return tiles view of inbox page
     */
    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String loadInbox(ModelMap model) {
        model.addAttribute("username", getCurrentUserName());
        List<Message> inboxList = mailService.getMessages(userService.getUser(getCurrentUserName()), new Folder(1,
                "INBOX"));
        Collections.reverse(inboxList);
        model.addAttribute("inbox", inboxList);
        return "mail/inbox";
    }

    /**
     * Loads outbox page, which contains messages from the deliveryList which are not send already and waiting
     * for scheduled sender task woke up
     *
     * @param model page model
     * @return tiles view of outbox page
     */
    @RequestMapping(value = "/outbox", method = RequestMethod.GET)
    public String loadOutbox(ModelMap model) {
        model.addAttribute("username", getCurrentUserName());
        List<Message> deliveryList =  mailService.getDeliveryList(userService.getUser(getCurrentUserName()));
        Collections.reverse(deliveryList);
        model.addAttribute("deliveryList", deliveryList);
        return "mail/outbox";
    }

    /**
     * Loads sent page, which contains messages which are already successfully sent. List is reversed to show the
     * latest messages on the top of the list.
     *
     * @param model page model
     * @return tiles view of sent page
     */
    @RequestMapping(value = "/sent", method = RequestMethod.GET)
    public String loadSent(ModelMap model) {
        model.addAttribute("username", getCurrentUserName());
        List<Message> sent = mailService.getMessages(userService.getUser(getCurrentUserName()), new Folder(3, "SENT"));
        Collections.reverse(sent);
        model.addAttribute("messages", sent);
        return "mail/sent";
    }

    /**
     * Loads a page for creating a new message.
     *
     * @param model page model
     * @return tiles view if new_message page
     */
    @RequestMapping(value = "/new_message", method = RequestMethod.GET)
    public String loadNewMessage(ModelMap model) {
        model.addAttribute("username", getCurrentUserName());
        model.addAttribute("newMessage", new Message());
        return "mail/new_message";
    }

    /**
     * Used for auto complete. Loads a list of addresses from current users address book which contains a symbols from
     * user input
     *
     * @param email_address user input line
     * @return list of email addresses
     */
    @RequestMapping(value = "/new_message/getAddressBook", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getAddressBook(@RequestParam String email_address) {
        return addressBookService.searchInAddressBook(email_address, userService.getUser(getCurrentUserName())
                .getUser_id());
    }

    /**
     * Creates a new message with receivers which are chosen in "Address book" page by checkboxes.
     *
     * @param receiversId requestParams which are parsed to get the actual email addresses of receivers
     * @param model       page model
     * @return Message object with filled receivers property
     */
    @RequestMapping(value = "/new_address_message", method = RequestMethod.POST)
    public String messageFromAddressBook(@RequestParam(value = "ReceiversId",
            required = false) Integer[] receiversId, ModelMap model) {
        String receivers = "";
        if (receiversId != null && receiversId.length > 0) {
            for (Integer id : receiversId) {
                receivers += addressBookService.getAddress(id).getEmail_address() + ", ";
            }
        }
        model.addAttribute("username", getCurrentUserName());
        Message msg = new Message();
        msg.setReceivers(receivers);
        model.addAttribute("newMessage", msg);
        return "mail/new_message";
    }

    /**
     * Validates the user input of new messages and adds the message to deliveryList
     *
     * @param newMessage Object of Message to send
     * @param result     validation result
     * @param model      page model
     * @return tiles view of new_message page to show if it is added to List successfully
     */
    @RequestMapping(value = "/new_message", method = RequestMethod.POST)
    public String sendNewMessage(@Valid @ModelAttribute("newMessage") Message newMessage,
                                 final BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("username", getCurrentUserName());
            return "mail/new_message";
        }
        newMessage.setUser(userService.getUser(getCurrentUserName()));
        newMessage.setSender(newMessage.getUser().getEmail());
        try {
            mailService.addMessageToDelivery(newMessage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        model.addAttribute("message", "Message added to queue");
        model.addAttribute("username", getCurrentUserName());
        model.addAttribute("newMessage", new Message());
        return "mail/new_message";
    }
}
