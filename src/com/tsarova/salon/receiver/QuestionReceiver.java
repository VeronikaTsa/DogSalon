package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.Question;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.FeedbackRepository;
import com.tsarova.salon.repository.impl.QuestionRepository;
import com.tsarova.salon.specification.Specification;
import com.tsarova.salon.specification.impl.QuestionSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionReceiver {
    private static Logger logger = LogManager.getLogger();

    public static boolean delQuestion(Long questionId) throws ReceiverException {
        if(questionId!=null){
            Question question = new Question(questionId);
            Repository<Question> questionRepository = new QuestionRepository();
            try {
                System.out.println("QUESTION ID: " + question.getId());
                return questionRepository.remove(question);
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static boolean addQuestion(String questionContent, User user) throws ReceiverException {
        if (!questionContent.isEmpty()) {
            Question question = new Question(user.getUserId(), questionContent);
            Repository<Question> questionRepository = new QuestionRepository();

            try {
                return questionRepository.add(question);
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }


    public static List<Question> receiveQuestionNotAnsweredList()throws ReceiverException{

        List<Question> questionNotAnsweredList = new ArrayList<>();


        Repository questionRepository = new QuestionRepository();
        try {
            questionNotAnsweredList = questionRepository.findAll(); //может optional
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return questionNotAnsweredList;
    }
}
