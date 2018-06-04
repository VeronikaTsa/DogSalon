package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class AnswerRepository implements Repository<Answer> {
    private static Logger logger = LogManager.getLogger();

    /**
     * This method tries to add received {@param answer} to database.
     * It creates connection and fills <tt>PreparedStatement</tt> with
     * {@param answer} fields. Finally it closes connection.
     *
     * @param answer
     * @return <tt>boolean</tt> that means if answer added or not
     * @throws RepositoryException
     * @see java.sql.PreparedStatement
     */
    @Override
    public boolean add(Answer answer) throws RepositoryException {
        final String SQL_REPLACE_ANSWER = SQLQuery.REPLACE_ANSWER;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_REPLACE_ANSWER);
            statement.setString(1, answer.getId().toString());
            statement.setString(2, answer.getUserId().toString());
            statement.setString(3, answer.getContent());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
                ConnectionPool.getInstance().closeConnection(connection);
        }
        return false;
    }

    /**
     * This method tries to remove received {@param answer} to database.
     * It creates connection and fills <tt>PreparedStatement</tt> with
     * {@param answer} id. Finally it closes connection.
     *
     * @param answer
     * @return <tt>boolean</tt> that means if answer removed or not
     * @throws RepositoryException
     * @see java.sql.PreparedStatement
     */
    @Override
    public boolean remove(Answer answer) throws RepositoryException {
        final String SQL_DELETE_ANSWER = SQLQuery.DELETE_ANSWER;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_DELETE_ANSWER);
            statement.setString(1, answer.getId().toString());
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
                ConnectionPool.getInstance().closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Answer answer) throws RepositoryException {
        return false;
    }

    @Override
    public Answer find(Answer answer) throws RepositoryException {
        return null;
    }

    @Override
    public List<Answer> findAll() throws RepositoryException {
        return null;
    }

    @Override
    public List<Answer> query(Specification<Answer> specification) throws RepositoryException {
        return specification.execute();
    }
}