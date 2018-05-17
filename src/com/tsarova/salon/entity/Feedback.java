package com.tsarova.salon.entity;

import java.sql.Date;

public class Feedback implements Entity {
    private Long userId;
    private String userLogin;
    private String content;
    private Date createTime;
    private Long id;
    private String timeOfCreating;


    public Feedback(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Feedback(String userLogin, String content) {
        this.userLogin = userLogin;
        this.content = content;
    }

    public Feedback(String content, Date createTime, Long id) {
        this.content = content;
        this.createTime = createTime;
        this.id = id;
    }

    public Feedback(String content, Date createTime, Long id, String userLogin) {
        this.userLogin = userLogin;
        this.content = content;
        this.createTime = createTime;
        this.id = id;
    }

    public Feedback(String content, String timeOfCreating, Long id, String userLogin) {
        this.userLogin = userLogin;
        this.content = content;
        this.timeOfCreating = timeOfCreating;
        this.id = id;
    }

    public String getTimeOfCreating() {
        return timeOfCreating;
    }

    public void setTimeOfCreating(String timeOfCreating) {
        this.timeOfCreating = timeOfCreating;
    }

    public Feedback(Long id) {
        this.id = id;
    }

    public Feedback(String content) {
        this.content = content;
    }

    public Feedback(String content, Date createTime) {
        this.content = content;
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
