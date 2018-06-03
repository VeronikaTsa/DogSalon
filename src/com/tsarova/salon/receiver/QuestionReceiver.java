package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Question;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.QuestionRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class QuestionReceiver {
    private static Logger logger = LogManager.getLogger();

    public static boolean removeQuestion(Long questionId) throws ReceiverException {
        if (questionId != null) {
            Question question = new Question(questionId);
            Repository<Question> questionRepository = new QuestionRepository();
            return ReceiverValidator.remove(questionRepository, question);
        }
        return false;
    }

    public static boolean addQuestion(String questionContent, User user) throws ReceiverException {
        if (!questionContent.isEmpty()) {
            Question question = new Question(user.getUserId(), questionContent);
            Repository<Question> questionRepository = new QuestionRepository();
            return ReceiverValidator.add(questionRepository, question);
        }
        return false;
    }


    public static List<Question> receiveQuestionNotAnsweredList() throws ReceiverException {
        List<Question> questionNotAnsweredList;
        Repository<Question> questionRepository = new QuestionRepository();
        try {
            questionNotAnsweredList = questionRepository.findAll();
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return questionNotAnsweredList;
    }
}