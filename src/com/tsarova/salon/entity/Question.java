package com.tsarova.salon.entity;

import java.sql.Date;

public class Question implements Entity {
    private String userLogin;
    private Long id;
    private Long userId;
    private String content;
    private Date createTime;

    public Question(Long id) {
        this.id = id;
    }

    public Question(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Question(String content, Date createTime, Long id, String userLogin) {
        this.userLogin = userLogin;
        this.content = content;
        this.createTime = createTime;
        this.id = id;
    }

    public Question(Long id, Long userId, String content, Date createTime) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createTime = createTime;
    }

    public Question(String content, Date createTime) {
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

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
