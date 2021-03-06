package com.teaminternational.mail.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */

@Entity
@Table
public class Address {
    @Id
    @Column
    @GeneratedValue
    private Integer address_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty(message = "email must be not empty")
    @Email(message = "wrong email format")
    @Size(max = 60, message = "you have too long email, plz make it shorter")
    @Column
    private String email_address;

    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer id) {
        this.address_id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
