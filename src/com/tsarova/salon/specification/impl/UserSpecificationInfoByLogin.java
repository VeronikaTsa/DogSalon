package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserContent;
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

public class UserSpecificationInfoByLogin implements Specification<User> {
    private static Logger logger = LogManager.getLogger();


    private String desiredLogin;
    private List<User> userList = new ArrayList<>();

    public UserSpecificationInfoByLogin(String desiredLogin) {
        this.desiredLogin = desiredLogin;
    }

    @Override
    public List<User> execute() throws RepositoryException {
        String sql = SQLQuery.INFO_BY_LOGIN;
        User user = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSetUser = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            System.out.println("ИЩЕМ ПО ЛОГИНУ: " + desiredLogin);
            statement.setString(1, desiredLogin);
            resultSetUser = statement.executeQuery();
            if (resultSetUser.next()) {
                System.out.println("нашли того по логину, о ком инфу хотим посмотреть");
                user = new User();
                user.setLogin(resultSetUser.getString("login"));
                user.setEmail(resultSetUser.getString("email"));


                PreparedStatement statementUserContent = null;
                ResultSet resultSetUserContent = null;
                String sqlUserContent = SQLQuery.DEFINE_USER_CONTENT;

                statementUserContent = connection.prepareStatement(sqlUserContent);
                statementUserContent.setString(1, String.valueOf(user.getUserId()));
                resultSetUserContent = statementUserContent.executeQuery();

                if (resultSetUserContent.next()) {

                    UserContent userContent = new UserContent(resultSetUserContent.getString("first_name"),
                            resultSetUserContent.getString("last_name"),
                            resultSetUserContent.getString("telephone"),
                            resultSetUserContent.getString("picture"));

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


        } catch (SQLException | NullPointerException |
                ConnectionPoolException e)

        {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally

        {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return userList;        //????????????????    }
    }
}
