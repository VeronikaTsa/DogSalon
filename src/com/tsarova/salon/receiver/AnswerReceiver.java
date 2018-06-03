package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.AnswerRepository;

/**
 * @author Veronika Tsarova
 */
public class AnswerReceiver {

    public static boolean addAnswer(Long questionId, String answerContent, User user) throws ReceiverException {
        if(!answerContent.isEmpty()){
            Answer answer = new Answer(user.getLogin(), answerContent, questionId, user.getUserId());
            Repository<Answer> answerRepository = new AnswerRepository();
            return ReceiverValidator.add(answerRepository, answer);
        }
        return false;
    }

    public static boolean removeAnswer(Long answerId) throws ReceiverException {
        if (answerId != null) {
            Answer answer = new Answer(answerId);
            Repository<Answer> answerRepository = new AnswerRepository();
            return ReceiverValidator.remove(answerRepository, answer);
        }
        return false;
    }
}
