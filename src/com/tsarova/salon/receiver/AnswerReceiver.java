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
     * sends it to {@code Repository} via {@code ReceiverValidator} method.
     *
     * @param questionId corresponds to answer id
     * @param answerContent corresponds to answer content
     * @param user corresponds to answer author
     * @return <tt>boolean</tt> that means if answer added or not
     * @throws ReceiverException when receives {@code RepositoryException}
     * @see com.tsarova.salon.entity.Answer
     * @see com.tsarova.salon.repository.Repository
     * @see com.tsarova.salon.repository.impl.AnswerRepository
     * @see com.tsarova.salon.receiver.ReceiverValidator
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
     * {@code Answer} and sends it to {@code Repository}  via {@code ReceiverValidator} method.
     *
     * @param answerId corresponds to answer id
     * @return <tt>boolean</tt> that means if answer deleted or not
     * @throws ReceiverException when receives {@code RepositoryException}
     * @see com.tsarova.salon.entity.Answer
     * @see com.tsarova.salon.repository.Repository
     * @see com.tsarova.salon.repository.impl.AnswerRepository
     * @see com.tsarova.salon.receiver.ReceiverValidator
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
