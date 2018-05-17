package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserContent;
import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.entity.UserSex;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSpecificationByEmail implements Specification<User>{
    private static Logger logger = LogManager.getLogger();

    private static final String SQL_IF_USER_EXIST = SQLQuery.IF_EXIST_USER;


    private String desiredEmail;
    private List<User> userList = new ArrayList<>();

    public UserSpecificationByEmail(String desiredEmail) {
        this.desiredEmail = desiredEmail;
    }

    @Override
    public List<User> execute() throws RepositoryException {

        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSetUser;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(SQL_IF_USER_EXIST);
            statement.setString(1, desiredEmail);
            resultSetUser = statement.executeQuery();
            if (resultSetUser.next()) {
                return null; //??????????????????
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return new ArrayList<User>();        //????????????????
    }
}
