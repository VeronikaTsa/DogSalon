package com.tsarova.salon.entity;

import java.sql.Date;

/**
 * @author Veronika Tsarova
 */
public class Question implements Entity {
    private String userLogin;
    private Long id;
    private Long userId;
    private String content;
    private Date createTime;

    public Question(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;

        Question question = (Question) o;

        if (getUserLogin() != null ? !getUserLogin().equals(question.getUserLogin()) : question.getUserLogin() != null)
            return false;
        if (getId() != null ? !getId().equals(question.getId()) : question.getId() != null) return false;
        if (getUserId() != null ? !getUserId().equals(question.getUserId()) : question.getUserId() != null)
            return false;
        if (getContent() != null ? !getContent().equals(question.getContent()) : question.getContent() != null)
            return false;
        return getCreateTime() != null ? getCreateTime().equals(question.getCreateTime()) : question.getCreateTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserLogin() != null ? getUserLogin().hashCode() : 0;
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "userLogin='" + userLogin + '\'' +
                ", id=" + id +
                ", userId=" + userId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}