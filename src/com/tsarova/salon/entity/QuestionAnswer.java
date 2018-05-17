package com.tsarova.salon.entity;

import java.sql.Date;

public class QuestionAnswer implements Entity {
    private String questionUserLogin;
    private Long questionAnswerId;
    private String questionContent;
    private Date questionCreateTime;
    private String answerUserLogin;
    private String answerContent;
    private Date answerCreateTime;
    private Date answerLastUpdate;

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

    public Date getAnswerLastUpdate() {
        return answerLastUpdate;
    }

    public void setAnswerLastUpdate(Date answerLastUpdate) {
        this.answerLastUpdate = answerLastUpdate;
    }
}
