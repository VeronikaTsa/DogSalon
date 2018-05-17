package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.AnswerRepository;
import com.tsarova.salon.repository.impl.FeedbackRepository;

public class AnswerReceiver {
    public static boolean addAnswer(Long questionId, String answerContent, User user) throws ReceiverException {
        if(answerContent != null){
            Answer answer = new Answer(user.getLogin(), answerContent, questionId, user.getUserId());
            Repository<Answer> answerRepository = new AnswerRepository();
            try {
                if(answerRepository.add(answer)){
                    return true;
                }
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
