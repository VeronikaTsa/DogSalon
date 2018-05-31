package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.AnswerRepository;
import com.tsarova.salon.repository.impl.FeedbackRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnswerReceiver {
    private static Logger logger = LogManager.getLogger();

    public static boolean addAnswer(Long questionId, String answerContent, User user) throws ReceiverException {
        if(!answerContent.isEmpty()){
            Answer answer = new Answer(user.getLogin(), answerContent, questionId, user.getUserId());
            Repository<Answer> answerRepository = new AnswerRepository();
            try {
                if(answerRepository.add(answer)){
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static boolean removeAnswer(Long answerId) throws ReceiverException {
        if (answerId != null) {//может, не надо
            Answer answer = new Answer(answerId);
            Repository<Answer> answerRepository = new AnswerRepository();
            try {
                if (answerRepository.remove(answer)) {
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }
}
