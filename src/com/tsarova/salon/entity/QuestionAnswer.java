package com.tsarova.salon.entity;

import java.sql.Date;

/**
 * @author Veronika Tsarova
 */
public class QuestionAnswer implements Entity {
    private String questionUserLogin;
    private Long questionAnswerId;
    private String questionContent;
    private Date questionCreateTime;
    private String answerUserLogin;
    private String answerContent;
    private Date answerCreateTime;

    public QuestionAnswer(){}

    public QuestionAnswer(Long questionAnswerId, String questionUserLogin, String questionContent, Date questionCreateTime,
                          String answerUserLogin, String answerContent, Date answerCreateTime) {
        this.questionUserLogin = questionUserLogin;
        this.questionAnswerId = questionAnswerId;
        this.questionContent = questionContent;
        this.questionCreateTime = questionCreateTime;
        this.answerUserLogin = answerUserLogin;
        this.answerContent = answerContent;
        this.answerCreateTime = answerCreateTime;
    }

    public String getQuestionUserLogin() {
        return questionUserLogin;
    }

    public void setQuestionUserLogin(String questionUserLogin) {
        this.questionUserLogin = questionUserLogin;
    }

    public Long getQuestionAnswerId() {
        return questionAnswerId;
    }

    public void setQuestionAnswerId(Long questionAnswerId) {
        this.questionAnswerId = questionAnswerId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Date getQuestionCreateTime() {
        return questionCreateTime;
    }

    public void setQuestionCreateTime(Date questionCreateTime) {
        this.questionCreateTime = questionCreateTime;
    }

    public String getAnswerUserLogin() {
        return answerUserLogin;
    }

    public void setAnswerUserLogin(String answerUserLogin) {
        this.answerUserLogin = answerUserLogin;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Date getAnswerCreateTime() {
        return answerCreateTime;
    }

    public void setAnswerCreateTime(Date answerCreateTime) {
        this.answerCreateTime = answerCreateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionAnswer)) return false;

        QuestionAnswer that = (QuestionAnswer) o;

        if (getQuestionUserLogin() != null ? !getQuestionUserLogin().equals(that.getQuestionUserLogin()) : that.getQuestionUserLogin() != null)
            return false;
        if (getQuestionAnswerId() != null ? !getQuestionAnswerId().equals(that.getQuestionAnswerId()) : that.getQuestionAnswerId() != null)
            return false;
        if (getQuestionContent() != null ? !getQuestionContent().equals(that.getQuestionContent()) : that.getQuestionContent() != null)
            return false;
        if (getQuestionCreateTime() != null ? !getQuestionCreateTime().equals(that.getQuestionCreateTime()) : that.getQuestionCreateTime() != null)
            return false;
        if (getAnswerUserLogin() != null ? !getAnswerUserLogin().equals(that.getAnswerUserLogin()) : that.getAnswerUserLogin() != null)
            return false;
        if (getAnswerContent() != null ? !getAnswerContent().equals(that.getAnswerContent()) : that.getAnswerContent() != null)
            return false;
        return getAnswerCreateTime() != null ? getAnswerCreateTime().equals(that.getAnswerCreateTime()) : that.getAnswerCreateTime() == null;
    }

    @Override
    public int hashCode() {
        int result = getQuestionUserLogin() != null ? getQuestionUserLogin().hashCode() : 0;
        result = 31 * result + (getQuestionAnswerId() != null ? getQuestionAnswerId().hashCode() : 0);
        result = 31 * result + (getQuestionContent() != null ? getQuestionContent().hashCode() : 0);
        result = 31 * result + (getQuestionCreateTime() != null ? getQuestionCreateTime().hashCode() : 0);
        result = 31 * result + (getAnswerUserLogin() != null ? getAnswerUserLogin().hashCode() : 0);
        result = 31 * result + (getAnswerContent() != null ? getAnswerContent().hashCode() : 0);
        result = 31 * result + (getAnswerCreateTime() != null ? getAnswerCreateTime().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionAnswer{" +
                "questionUserLogin='" + questionUserLogin + '\'' +
                ", questionAnswerId=" + questionAnswerId +
                ", questionContent='" + questionContent + '\'' +
                ", questionCreateTime=" + questionCreateTime +
                ", answerUserLogin='" + answerUserLogin + '\'' +
                ", answerContent='" + answerContent + '\'' +
                ", answerCreateTime=" + answerCreateTime +
                '}';
    }
}