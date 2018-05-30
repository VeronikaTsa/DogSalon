package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Answer;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
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

public class AnswerRepository implements Repository<Answer> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(Answer answer) throws RepositoryException {
        String sql = SQLQuery.ADD_ANSWER;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, answer.getId().toString());
            statement.setString(2, answer.getUserId().toString());
            statement.setString(3, answer.getContent());
            if (statement.executeUpdate() > 0) {
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
    public boolean remove(Answer answer) throws RepositoryException {
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
