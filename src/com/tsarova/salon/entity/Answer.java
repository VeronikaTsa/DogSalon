package com.tsarova.salon.entity;

import java.sql.Date;

public class Answer implements Entity{

    private String userLogin;
    private Long id;
    private Long userId;
    private String content;
    private Date createTime;
    private Date lastUpdate;


    public Answer(String userLogin, String content, Long id) {
        this.userLogin = userLogin;
        this.content = content;
        this.id = id;
    }

    public Answer(String userLogin, String content, Long id, Long userId) {
        this.userLogin = userLogin;
        this.content = content;
        this.id = id;
        this.userId = userId;
    }

    public Answer(Long id, Long userId, String content, Date createTime, Date lastUpdate) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createTime = createTime;
        this.lastUpdate = lastUpdate;
    }

    public Answer(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
