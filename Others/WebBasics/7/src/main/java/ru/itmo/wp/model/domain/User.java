package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private long id;
    private String login;
    private Boolean admin;
    private Date creationTime;

    public User() {
        this.login = null;
        this.admin = null;
        this.creationTime = null;
    }
    public User(User user) {
        this.id = user.id;
        this.login = user.login;
        this.admin = user.admin;
        this.creationTime = user.creationTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
