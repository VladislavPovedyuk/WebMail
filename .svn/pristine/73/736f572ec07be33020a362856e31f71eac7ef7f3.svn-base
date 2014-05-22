package com.teaminternational.mail.service;

import com.teaminternational.mail.dao.UserDAO;
import com.teaminternational.mail.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Vladislav Povedyuk
 * Date: 11.10.13
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    /**
     * Add a new user
     *
     * @param user new user
     */
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    /**
     * Get user from database by id
     *
     * @param id needed user id
     * @return User object retrieved from database
     */
    @Transactional
    public User getUser(Integer id) {
        return userDAO.getUser(id);
    }

    /**
     * Get user from database by email
     *
     * @param email needed user email
     * @return User object retrieved from database
     */
    @Transactional
    public User getUser(String email) {
        return userDAO.getUser(email);
    }

    /**
     * Gets the list of registered users
     *
     * @return users list
     */
    @Transactional
    public List<User> userList() {
        return userDAO.userList();
    }

    /**
     * updates user data after editing
     *
     * @param user edited user
     */
    @Transactional
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }
}
