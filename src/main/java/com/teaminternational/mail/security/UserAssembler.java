package com.teaminternational.mail.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * User: Vladislav Povedyuk
 * Date: 15.10.13
 */
@Service("assembler")
class UserAssembler {
    /**
     * Builds the UserDetails object with User data retrieved from database
     *
     * @param user user from database
     * @return UserDetails object
     */
    @Transactional(readOnly = true)
    User buildUserFromUserEntity(com.teaminternational.mail.domain.User user) {

        String username = user.getEmail();
        String password = user.getPassword();
        boolean enabled = true;
        if (user.getIsDisabled()) {
            enabled = false;
        }
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthorityImpl(user.getRole()));
        return new User(username, password, enabled,
                accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
