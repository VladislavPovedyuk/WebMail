package com.teaminternational.mail.dao;

import com.teaminternational.mail.domain.Folder;
import com.teaminternational.mail.domain.Message;
import com.teaminternational.mail.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */

@Repository
public class MessageDAOImpl implements MessageDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveMessage(Message message) {
        sessionFactory.getCurrentSession().save(message);
    }

    @Override
    public void saveIncomingMessages(List<Message> message, User user) {
        for (Message incoming : message){
            incoming.setUser(user);
            incoming.setFolder(new Folder(1, "INBOX"));
            saveMessage(incoming);
        }
    }

    @Override
    public void updateMessage(Message message) {
        sessionFactory.getCurrentSession().update(message);
    }

    @Override
    public void deleteMessage(Message message) {
        if (message != null){
            sessionFactory.getCurrentSession().delete(message);
        }
    }

    @Override
    public List<Message> getMessages(User user, Folder folder) {
        return sessionFactory.getCurrentSession().createQuery("FROM Message as m WHERE m.user = ? AND m.folder = ?")
                .setParameter(0, user)
                .setParameter(1, folder).list();
    }

    @Override
    public List<Message> getMessagesForDelivery() {
        return sessionFactory.getCurrentSession().createQuery("FROM Message WHERE folder = ?")
                .setParameter(0, new Folder(2, "OUTBOX")).list();
    }
}
