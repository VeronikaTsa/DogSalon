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

public class FeedbackRepository implements Repository<Feedback> {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public boolean add(Feedback feedback) throws RepositoryException {
        String sql = SQLQuery.ADD_FEEDBACK;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(feedback.getUserId()));
            statement.setString(2, feedback.getContent());
            if(statement.executeUpdate()>0){
                return true;
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean remove(Feedback feedback) throws RepositoryException {
        String sql = SQLQuery.DELETE_FEEDBACK;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, feedback.getId().toString());
            if(statement.executeUpdate()>0){
                return true;
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
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
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_FIND_ALL_FEEDBACKS);
            resultSet = statement.executeQuery();
            while (!resultSet.isLast()){
                if (resultSet.next()) {
                    String feedbackContent = resultSet.getString("content");
                    Date feedbackCreateTime = new Date(resultSet.getTimestamp("create_time").getTime());
                    String timeOfCreate = resultSet.getString("create_time");
                    Long feedbackId = Long.valueOf(resultSet.getString("feedback_id"));
                    String userLogin = resultSet.getString("login");
                    //feedbackList.add(new Feedback(feedbackContent, feedbackCreateTime, feedbackId, userLogin));//??????????????????
                    feedbackList.add(new Feedback(feedbackContent, timeOfCreate, feedbackId, userLogin));//??????????????????

                }
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return feedbackList;
    }

    @Override
    public List<Feedback> query(Specification<Feedback> specification) throws RepositoryException {
        return specification.execute();
    }
}
