package com.tsarova.salon.entity;

import java.util.List;

public class User implements Entity {
    //сериализация

    private Long userId;
    private String email;
    private String login;
    private String password;
    private UserRole role;
    private UserContent userContent;
    private List<Long> questionIdList;
    private List<Long> registerIdList;
    private List<Long> feedbackIdList;

    public User() {
    }

    public User(String email, String password, String login) {
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(Long userId, String email, String password, UserRole role, String login) {
        this.userId = userId;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(Long userId, String email, String password,
                UserRole role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password, String login,
                UserRole role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }

    public List<Long> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(List<Long> questionIdList) {
        this.questionIdList = questionIdList;
    }

    public List<Long> getRegisterIdList() {
        return registerIdList;
    }

    public void setRegisterIdList(List<Long> registerIdList) {
        this.registerIdList = registerIdList;
    }

    public List<Long> getFeedbackIdList() {
        return feedbackIdList;
    }

    public void setFeedbackIdList(List<Long> feedbackIdList) {
        this.feedbackIdList = feedbackIdList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!getUserId().equals(user.getUserId())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getLogin().equals(user.getLogin())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (getRole() != user.getRole()) return false;
        if (getUserContent() != null ? !getUserContent().equals(user.getUserContent()) : user.getUserContent() != null)
            return false;
        if (getQuestionIdList() != null ? !getQuestionIdList().equals(user.getQuestionIdList()) : user.getQuestionIdList() != null)
            return false;
        if (getRegisterIdList() != null ? !getRegisterIdList().equals(user.getRegisterIdList()) : user.getRegisterIdList() != null)
            return false;
        return getFeedbackIdList() != null ? getFeedbackIdList().equals(user.getFeedbackIdList()) : user.getFeedbackIdList() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getLogin().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getRole().hashCode();
        result = 31 * result + (getUserContent() != null ? getUserContent().hashCode() : 0);
        result = 31 * result + (getQuestionIdList() != null ? getQuestionIdList().hashCode() : 0);
        result = 31 * result + (getRegisterIdList() != null ? getRegisterIdList().hashCode() : 0);
        result = 31 * result + (getFeedbackIdList() != null ? getFeedbackIdList().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userContent=" + userContent +
                ", questionIdList=" + questionIdList +
                ", registerIdList=" + registerIdList +
                ", feedbackIdList=" + feedbackIdList +
                '}';
    }
}
