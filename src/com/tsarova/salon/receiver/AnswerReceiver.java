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

    /**
     * This method checks received parameter {@param answerContent} on nonemptiness and,
     * if it is not empty, creates <tt>Answer</tt> object with received parameters and
     * sends it to <tt>Repository</tt>.
     *
     * @param questionId
     * @param answerContent
     * @param user
     * @return <tt>boolean</tt> that means if answer added or not
     * @throws ReceiverException
     * @see com.tsarova.salon.entity.Answer
     * @see com.tsarova.salon.repository.Repository
     * @see com.tsarova.salon.repository.impl.AnswerRepository
     */
    public static boolean addAnswer(Long questionId, String answerContent, User user) throws ReceiverException {
        if(!answerContent.isEmpty()){
            Answer answer = new Answer(user.getLogin(), answerContent, questionId, user.getUserId());
            Repository<Answer> answerRepository = new AnswerRepository();
            return ReceiverValidator.add(answerRepository, answer);
        }
        return false;
    }

    /**
     * This method checks if answer id is null. If not, it creates
     * <tt>Answer</tt> and sends it to <tt>Repository</tt>.
     *
     * @param answerId
     * @return <tt>boolean</tt> that means if answer deleted or not
     * @throws ReceiverException
     * @see com.tsarova.salon.entity.Answer
     * @see com.tsarova.salon.repository.Repository
     * @see com.tsarova.salon.repository.impl.AnswerRepository
     */
    public static boolean removeAnswer(Long answerId) throws ReceiverException {
        if (answerId != null) {
            Answer answer = new Answer(answerId);
            Repository<Answer> answerRepository = new AnswerRepository();
            return ReceiverValidator.remove(answerRepository, answer);
        }
        return false;
    }
}
