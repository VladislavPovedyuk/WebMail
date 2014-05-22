package com.teaminternational.mail.dao;

import com.teaminternational.mail.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 11.10.13
 */

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addUser(User user) {
        user.setRole("ROLE_USER");
        user.setIsDisabled(false);
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User getUser(Integer id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public User getUser(String email) {
        return (User) sessionFactory.getCurrentSession().createQuery("FROM User WHERE email = ?")
                .setParameter(0, email).uniqueResult();
    }

    @Override
    public List<User> userList() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").list();
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}
