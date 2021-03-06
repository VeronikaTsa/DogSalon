package com.tsarova.salon.command;

import com.tsarova.salon.command.impl.*;

/**
 * @author Veronika Tsarova
 */
public enum CommandEnum {
    LOGIN(new LogInCommand()),
    SIGNUP(new SignUpCommand()),
    LOGOUT(new LogOutCommand()),
    SIGNUPCONTINUE(new SignUpContinueCommand()),
    SIGNUPEXPERT(new SignUpExpertCommand()),
    QUESTIONNOTANSWERED(new QuestionNotAnsweredCommand()),
    FEEDBACK(new FeedbackCommand()),
    FEEDBACKADD(new FeedbackAddCommand()),
    FEEDBACKDELETE( new FeedbackDeleteCommand()),
    USERINFO(new UserInfoCommand()),
    ANSWERADD(new AnswerAddCommand()),
    QUESTIONANSWER(new QuestionAnswerCommand()),
    QUESTIONASK(new QuestionAskCommand()),
    QUESTIONDELETE(new QuestionDeleteCommand()),
    SERVICE(new ServiceCommand()),
    SERVICEADD(new ServiceAddCommand()),
    SERVICEDELETE(new ServiceDeleteCommand()),
    SERVICEUPDATE(new ServiceUpdateCommand()),
    USERUPDATE(new UserUpdateCommand()),
    ANSWERDELETE(new AnswerDeleteCommand());


    private Command command;
    CommandEnum(Command command) {
        this.command = command;
    }
    public Command getValue() {
        return command;
    }
}
