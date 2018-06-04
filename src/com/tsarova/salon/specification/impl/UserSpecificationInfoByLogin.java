package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserContent;
import com.tsarova.salon.entity.UserSex;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
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
public class UserSpecificationInfoByLogin implements Specification<User> {
    private static Logger logger = LogManager.getLogger();


    private String desiredLogin;
    private List<User> userList = new ArrayList<>();

    public UserSpecificationInfoByLogin(String desiredLogin) {
        this.desiredLogin = desiredLogin;
    }

    @Override
    public List<User> execute() throws RepositoryException {
        final String SQL_INFO_BY_LOGIN = SQLQuery.INFO_BY_LOGIN;
        final String SQL_USER_CONTENT = SQLQuery.DEFINE_USER_CONTENT;

        User user;
        Connection connection = null;
        PreparedStatement statement;
        ResultSet resultSetUser;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_INFO_BY_LOGIN);
            statement.setString(1, desiredLogin);
            resultSetUser = statement.executeQuery();
            if (resultSetUser.next()) {
                user = new User();
                user.setLogin(resultSetUser.getString("login"));
                user.setEmail(resultSetUser.getString("email"));
                user.setUserId((long) resultSetUser.getInt("user_id"));

                PreparedStatement statementUserContent;
                ResultSet resultSetUserContent;

                statementUserContent = connection.prepareStatement(SQL_USER_CONTENT);
                statementUserContent.setString(1, String.valueOf(user.getUserId()));
                resultSetUserContent = statementUserContent.executeQuery();

                if (resultSetUserContent.next()) {
                    UserContent userContent = new UserContent(resultSetUserContent.getString("first_name"),
                            resultSetUserContent.getString("last_name"),
                            resultSetUserContent.getString("telephone"));
                    if (resultSetUserContent.getString("birthday") != null) {
                        userContent.setBirthday(Date.valueOf(resultSetUserContent.getString("birthday")));
                    }
                    if (resultSetUserContent.getString("sex") != null) {
                        userContent.setSex(UserSex.valueOf(resultSetUserContent.getString("sex").toUpperCase()));
                    }
                    user.setUserContent(userContent);
                }
                userList.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
                ConnectionPool.getInstance().closeConnection(connection);
        }
        return userList;
    }
}