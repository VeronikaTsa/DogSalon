package com.tsarova.salon.receiver;

import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.impl.FeedbackRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FeedbackReceiver {
    private static Logger logger = LogManager.getLogger();

    public static boolean addFeedback(String feedbackContent, User user) throws ReceiverException {
        if(!feedbackContent.isEmpty()){
            Feedback feedback = new Feedback(user.getUserId(), feedbackContent);
            Repository<Feedback> feedbackRepository = new FeedbackRepository();
            try {
                if(feedbackRepository.add(feedback)){
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static boolean removeFeedback(Long feedbackId) throws ReceiverException {
        if (feedbackId != null) {//может, не надо
            Feedback feedback = new Feedback(feedbackId);
            Repository<Feedback> feedbackRepository = new FeedbackRepository();
            try {
                if (feedbackRepository.remove(feedback)) {
                    return true;
                }
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        }
        return false;
    }

    public static List<Feedback> receiveFeedbackList() throws ReceiverException {
        List<Feedback> feedbackList;

            Repository feedbackRepository = new FeedbackRepository();
            try {
                feedbackList = feedbackRepository.findAll(); //может optional
            } catch (RepositoryException e) {
                logger.catching(Level.ERROR, e);
                throw new ReceiverException(e);
            }
        return feedbackList;
    }
}
