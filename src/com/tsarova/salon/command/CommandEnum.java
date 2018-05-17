package com.tsarova.salon.command;


import com.tsarova.salon.command.impl.*;

public enum CommandEnum {
    LOGIN(new LogInCommand()),
    SIGNUP(new SignUpCommand()),
    LOGOUT(new LogOutCommand()),
    SIGNUPCONTINUE(new SignUpContinueCommand()),
    SIGNUPEXPERT(new SignUpExpertCommand()),
    QUESTION(new QuestionCommand()),
    FEEDBACK(new FeedbackCommand()),
    FEEDBACKADD(new FeedbackAddCommand()),
    FEEDBACKDELETE( new FeedbackDeleteCommand()),
    USERINFO(new UserInfoCommand()),
    FEEDBACKUSER(new FeedbackUserCommand()),
    QUESTIONUSER(new QuestionUserCommand()),
    ANSWERADD(new AnswerAddCommand()),
    QUESTIONANSWER(new QuestionAnswerCommand()),
    QUESTIONASK(new QuestionAskCommand()),
    QUESTIONDELETE(new QuestionDeleteCommand()),
    SERVICE(new ServiceCommand()),
    SERVICEADD(new ServiceAddCommand()),
    SERVICEDELETE(new ServiceDeleteCommand());


    private Command command;
    CommandEnum(Command command) {
        this.command = command;
    }
    public Command getValue() {
        return command;
    }
}
