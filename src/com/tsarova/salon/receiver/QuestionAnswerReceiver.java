package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Question;
import com.tsarova.salon.entity.QuestionAnswer;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.QuestionAnswerRepository;
import com.tsarova.salon.repository.impl.QuestionRepository;
import com.tsarova.salon.specification.Specification;
import com.tsarova.salon.specification.impl.QuestionSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerReceiver {
    private static Logger logger = LogManager.getLogger();

    public static List<QuestionAnswer> receiveQuestionAnswerList()throws ReceiverException {
        List<QuestionAnswer> questionAnswerList;
        Repository questionAnswerRepository = new QuestionAnswerRepository();
        try {
            questionAnswerList = questionAnswerRepository.findAll(); //может optional
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
            throw new ReceiverException(e);
        }
        return questionAnswerList;
    }
}
