package com.tsarova.salon.entity;

import java.sql.Date;

public class UserContent implements Entity {
    private String firstName;
    private String lastName;
    private String telephone;
    private Date birthday;
    private UserSex sex;
    private String picture;

    public UserContent() {
    }

    public UserContent(String firstName, String lastName, String telephone, Date birthday, UserSex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.birthday = birthday;
        this.sex = sex;
    }

    public UserContent(String firstName, String lastName, String telephone, UserSex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.sex = sex;
    }

    public UserContent(String firstName, String lastName, String telephone, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.birthday = birthday;
    }

    public UserContent(String firstName, String lastName, String telephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
    }

    public UserContent(String firstName, String lastName, String telephone, String picture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.telephone = telephone;
        this.picture = picture;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public UserSex getSex() {
        return sex;
    }

    public void setSex(UserSex sex) {
        this.sex = sex;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserContent)) return false;

        UserContent that = (UserContent) o;

        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null) return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getTelephone() != null ? !getTelephone().equals(that.getTelephone()) : that.getTelephone() != null)
            return false;
        if (getBirthday() != null ? !getBirthday().equals(that.getBirthday()) : that.getBirthday() != null)
            return false;
        if (getSex() != that.getSex()) return false;
        return getPicture() != null ? getPicture().equals(that.getPicture()) : that.getPicture() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getTelephone() != null ? getTelephone().hashCode() : 0);
        result = 31 * result + (getBirthday() != null ? getBirthday().hashCode() : 0);
        result = 31 * result + (getSex() != null ? getSex().hashCode() : 0);
        result = 31 * result + (getPicture() != null ? getPicture().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserContent{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", telephone='" + telephone + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", picture='" + picture + '\'' +
                '}';
    }
}
