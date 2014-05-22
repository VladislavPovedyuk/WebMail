package com.teaminternational.mail.domain;

import javax.persistence.*;

/**
 * User: Vladislav Povedyuk
 * Date: 19.11.13
 */
@Entity
@Table
public class Folder {
    @Id
    @Column
    @GeneratedValue
    private Integer folder_id;

    @Column
    private String folderName;

    public Integer getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(Integer folder_id) {
        this.folder_id = folder_id;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Folder(){}

    public Folder(Integer folder_id, String folderName){
        this.folder_id = folder_id;
        this.folderName = folderName;
    }
}
