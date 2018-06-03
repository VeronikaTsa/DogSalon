package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.QuestionAnswer;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class QuestionAnswerRepository implements Repository<QuestionAnswer> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public boolean remove(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(QuestionAnswer questionAnswer) throws RepositoryException {
        return false;
    }

    @Override
    public QuestionAnswer find(QuestionAnswer questionAnswer) throws RepositoryException {
        return null;
    }

    @Override
    public List<QuestionAnswer> findAll() throws RepositoryException {
        final String SQL_FIND_QUESTIONS_ANSWERS = SQLQuery.FIND_QUESTIONS_ANSWERS;
        List<QuestionAnswer> questionAnswerList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_QUESTIONS_ANSWERS);
            resultSet = statement.executeQuery();
            while (!resultSet.isLast()) {
                if (resultSet.next()) {
                    Long questionAnswerId = (long) resultSet.getInt("question_answer_id");
                    String questionContent = resultSet.getString("question_content");
                    Date questionCreateTime = new Date(resultSet.getTimestamp("question_create_time").getTime());
                    String questionUserLogin = resultSet.getString("question_user_login");
                    String answerContent = resultSet.getString("answer_content");
                    Date answerCreateTime = new Date(resultSet.getTimestamp("answer_create_time").getTime());
                    String answerUserLogin = resultSet.getString("answer_user_login");
                    QuestionAnswer questionAnswer = new QuestionAnswer(questionAnswerId, questionUserLogin, questionContent,
                            questionCreateTime, answerUserLogin, answerContent, answerCreateTime);
                    questionAnswerList.add(questionAnswer);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return questionAnswerList;
    }

    @Override
    public List<QuestionAnswer> query(Specification<QuestionAnswer> specification) throws RepositoryException {
        return null;
    }
}
