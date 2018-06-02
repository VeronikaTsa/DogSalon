package com.tsarova.salon.entity;

import java.sql.Date;

/**
 * @author Veronika Tsarova
 */
public class Feedback implements Entity {
    private Long userId;
    private String userLogin;
    private String content;
    private Date createTime;
    private Long id;

    public Feedback(){}

    public Feedback(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Feedback(String content, Date createTime, Long id, String userLogin) {
        this.userLogin = userLogin;
        this.content = content;
        this.createTime = createTime;
        this.id = id;
    }

    public Feedback(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feedback)) return false;

        Feedback feedback = (Feedback) o;

        if (getUserId() != null ? !getUserId().equals(feedback.getUserId()) : feedback.getUserId() != null)
            return false;
        if (getUserLogin() != null ? !getUserLogin().equals(feedback.getUserLogin()) : feedback.getUserLogin() != null)
            return false;
        if (getContent() != null ? !getContent().equals(feedback.getContent()) : feedback.getContent() != null)
            return false;
        if (getCreateTime() != null ? !getCreateTime().equals(feedback.getCreateTime()) : feedback.getCreateTime() != null)
            return false;
        return getId() != null ? getId().equals(feedback.getId()) : feedback.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getUserLogin() != null ? getUserLogin().hashCode() : 0);
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", id=" + id +
                '}';
    }
}
