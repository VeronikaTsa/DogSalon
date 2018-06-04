package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class UserSpecificationByEmailOrLogin implements Specification<User> {
    private static Logger logger = LogManager.getLogger();

    private static final String SQL_IF_USER_EXIST = SQLQuery.IF_EXIST_USER;
    private static final String SQL_IF_EXIST_LOGIN = SQLQuery.IF_EXIST_LOGIN;

    private String desiredParameter;
    private RequiredParameterType requiredParameterType;

    private List<User> userList = new ArrayList<>();

    public UserSpecificationByEmailOrLogin(String desiredParameter, RequiredParameterType requiredParameterType) {
        this.desiredParameter = desiredParameter;
        this.requiredParameterType = requiredParameterType;
    }

    @Override
    public List<User> execute() throws RepositoryException {

        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSetUser;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            if (requiredParameterType == RequiredParameterType.LOGIN) {
                statement = connection.prepareStatement(SQL_IF_EXIST_LOGIN);
                statement.setString(1, desiredParameter);
            } else {
                statement = connection.prepareStatement(SQL_IF_USER_EXIST);
                statement.setString(1, desiredParameter);
            }
            resultSetUser = statement.executeQuery();
            if (resultSetUser.next()) {
                return null;
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
                ConnectionPool.getInstance().closeConnection(connection);
        }
        return userList;
    }

    public enum RequiredParameterType {
        EMAIL,
        LOGIN
    }
}