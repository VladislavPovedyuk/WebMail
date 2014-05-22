package com.teaminternational.mail.dao;

import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import com.teaminternational.mail.domain.User;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */
public interface MessageDAO {

    public void saveMessage(Message message);

    public void saveIncomingMessages(List<Message> message, User user);

    public void updateMessage(Message message);

    public void deleteMessage(Message message);

    public List<Message> getMessages(User user, Folder folder);

    public List<Message> getMessagesForDelivery();
}
