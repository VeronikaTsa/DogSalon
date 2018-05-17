package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserRole;
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

public class UserSpecificationSignUp implements Specification<User> {
    private static Logger logger = LogManager.getLogger();

    private String desiredEmail;
    private String desiredPassword;
    private String desiredLogin;
    private UserRole desiredRole;
    private List<User> userList = new ArrayList<>();

    public UserSpecificationSignUp(String desiredEmail, String desiredPassword, String desiredLogin, UserRole desiredRole) {
        this.desiredEmail = desiredEmail;
        this.desiredPassword = desiredPassword;
        this.desiredLogin = desiredLogin;
        this.desiredRole = desiredRole;
    }

    @Override
    public List<User> execute() throws RepositoryException {
        List<User> oneUser = new ArrayList<User>();


        String sql = SQLQuery.CREATE_USER;

        User user = null;
        Connection connection = null;
        PreparedStatement statement = null;
        //ResultSet rs = null;


        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, desiredEmail);
            statement.setString(2, desiredPassword);
            statement.setString(3, desiredLogin);
            statement.setString(4, desiredRole.getValue());
//            user.setRole(UserRole.valueOf(resultSetUser.getString("user_role").toUpperCase()));
//            user.setUserId(Long.valueOf(resultSetUser.getString("user_id")));


            if (statement.executeUpdate() > 0) {
                user = new User(desiredEmail, desiredPassword, desiredLogin, desiredRole);

                String sqlUser = SQLQuery.DEFINE_USER;
                PreparedStatement statementUser = null;
                ResultSet resultSetUser = null;


                statementUser = connection.prepareStatement(sqlUser);
                statementUser.setString(1, user.getEmail());
                statementUser.setString(2, user.getPassword());
                System.out.println("ищем с мэил и паролем: " + user.getEmail() + " " + user.getPassword());
                resultSetUser = statementUser.executeQuery();
                if (resultSetUser.next()) {
                    System.out.println("нашли");
                    //user.setRole(UserRole.valueOf(resultSetUser.getString("user_role").toUpperCase()));
                    user.setUserId(Long.valueOf(resultSetUser.getString("user_id")));
                    oneUser.add(user);
                    return oneUser;
                }
            }


        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException();
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return oneUser;

    }

}