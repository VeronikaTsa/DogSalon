package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
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
public class FeedbackRepository implements Repository<Feedback> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(Feedback feedback) throws RepositoryException {
        final String SQL_ADD_FEEDBACK = SQLQuery.ADD_FEEDBACK;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_ADD_FEEDBACK);
            statement.setString(1, String.valueOf(feedback.getUserId()));
            statement.setString(2, feedback.getContent());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPoolImpl.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public boolean remove(Feedback feedback) throws RepositoryException {
        final String SQL_DELETE_FEEDBACK = SQLQuery.DELETE_FEEDBACK;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_FEEDBACK);
            statement.setString(1, feedback.getId().toString());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPoolImpl.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public boolean update(Feedback feedback) throws RepositoryException {
        return false;
    }

    @Override
    public Feedback find(Feedback feedback) throws RepositoryException {
        return null;
    }

    @Override
    public List<Feedback> findAll() throws RepositoryException {
        final String SQL_FIND_ALL_FEEDBACKS = SQLQuery.FIND_FEEDBACKS;
        List<Feedback> feedbackList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSet;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_FEEDBACKS);
            resultSet = statement.executeQuery();
            while (!resultSet.isLast()) {
                if (resultSet.next()) {
                    String feedbackContent = resultSet.getString("content");
                    Date feedbackCreateTime = new Date(resultSet.getTimestamp("create_time").getTime());
                    Long feedbackId = Long.valueOf(resultSet.getString("feedback_id"));
                    String userLogin = resultSet.getString("login");
                    feedbackList.add(new Feedback(feedbackContent, feedbackCreateTime, feedbackId, userLogin));
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPoolImpl.getInstance().closeConnection(connection);
            }
        }
        return feedbackList;
    }

    @Override
    public List<Feedback> query(Specification<Feedback> specification) throws RepositoryException {
        return specification.execute();
    }
}
