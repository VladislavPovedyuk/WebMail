package com.teaminternational.mail.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User: Vladislav Povedyuk
 * Date: 08.10.13
 */
@Entity
@Table
public class User {
    @Id
    @Column
    @GeneratedValue
    private Integer user_id;

    @NotEmpty(message = "email must be not empty")
    @Email(message = "wrong email format")
    @Size(max = 60, message = "you have too long email, plz make it shorter")
    @Column
    private String email;

    @NotEmpty(message = "password must be not empty")
    @Size(max = 60, message = "you have too long password, plz make it shorter")
    @Column
    private String password;

    @Transient
    @Size(max = 60, message = "your password isn't so big")
    private String passwordConfirm;

    @Column
    private String role;

    @Column
    private boolean isDisabled;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean disabled) {
        isDisabled = disabled;
    }
}
