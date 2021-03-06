package com.teaminternational.mail.security;

import com.teaminternational.mail.dao.UserDAO;
import com.teaminternational.mail.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Vladislav Povedyuk
 * Date: 15.10.13
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAssembler assembler;

    /**
     * Method which used by Spring security to authenticate logged in user
     *
     * @param username used to get user from database to authorisation
     * @return UserDetails object
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, DataAccessException {

        User userEntity = null;
        try {
            if (username.trim().isEmpty()) {
                throw new BadCredentialsException("Username must not be empty");
            }
            userEntity = userDAO.getUser(username);
            if (userEntity == null) {
                throw new IllegalArgumentException("No such user in the database");
            }
        } catch (IllegalArgumentException ex) {
            throw new BadCredentialsException(ex.getMessage());
        }

        return assembler.buildUserFromUserEntity(userEntity);
    }


}
