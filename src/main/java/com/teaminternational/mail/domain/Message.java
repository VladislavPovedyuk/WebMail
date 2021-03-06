package com.teaminternational.mail.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * User: Vladislav Povedyuk
 * Date: 24.10.13
 */

@Entity
@Table
public class Message {
    @Id
    @Column
    @GeneratedValue
    private Integer message_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String sender;

    @NotEmpty(message = "receiver must be not empty")
    @Column
    private String receivers;

    @NotEmpty(message = "subject must be not empty")
    @Column
    private String subject;

    @NotEmpty(message = "text must be not empty")
    @Column(columnDefinition = "TEXT")
    private String text;

    @Column
    private Date date;

    @Column
    private boolean isRead;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Integer getMessage_id() {
        return message_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
