package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Question;
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
public class QuestionRepository implements Repository<Question> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(Question question) throws RepositoryException {
        final String SQL_ADD_QUESTION = SQLQuery.ADD_QUESTION;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_QUESTION);
            statement.setString(1, String.valueOf(question.getUserId()));
            statement.setString(2, question.getContent());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public boolean remove(Question question) throws RepositoryException {
        final String SQL_DELETE_QUESTION = SQLQuery.DELETE_QUESTION;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_QUESTION);
            statement.setString(1, question.getId().toString());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public boolean update(Question question) throws RepositoryException {
        return false;
    }

    @Override
    public Question find(Question question) throws RepositoryException {
        return null;
    }

    @Override
    public List<Question> findAll() throws RepositoryException {
        final String SQL_FIND_NOT_ANSWERED_QUESTIONS = SQLQuery.FIND_NOT_ANSWERED_QUESTIONS;
        List<Question> questionList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_NOT_ANSWERED_QUESTIONS);
            resultSet = statement.executeQuery();
            while (!resultSet.isLast()) {
                if (resultSet.next()) {
                    String questionContent = resultSet.getString("content");
                    Date questionCreateTime = new Date(resultSet.getTimestamp("create_time").getTime());
                    Long questionId = (long) resultSet.getInt("question_id");
                    String userLogin = resultSet.getString("author_login");
                    questionList.add(new Question(questionContent, questionCreateTime, questionId, userLogin));
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
        return questionList;
    }

    @Override
    public List<Question> query(Specification<Question> specification) throws RepositoryException {
        return specification.execute();
    }
}
