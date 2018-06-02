package com.tsarova.salon.entity;

/**
 * @author Veronika Tsarova
 */
public class User implements Entity {

    private Long userId;
    private String email;
    private String login;
    private String password;
    private UserRole role;
    private UserContent userContent;

    public User() {
    }

    public User(Long userId) {
        this.userId = userId;
    }

    public User(Long userId, String email, String login, String password) {
        this.userId = userId;
        this.email = email;
        this.login = login;
        this.password = password;
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

    public User(Long userId, String email, String login, String password, UserContent userContent) {
        this.userId = userId;
        this.email = email;
        this.login = login;
        this.userContent = userContent;
        this.password = password;
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

        if (getUserId() != null ? !getUserId().equals(user.getUserId()) : user.getUserId() != null) return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        if (getRole() != user.getRole()) return false;
        return getUserContent() != null ? getUserContent().equals(user.getUserContent()) : user.getUserContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getUserContent() != null ? getUserContent().hashCode() : 0);
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
                '}';
    }
}