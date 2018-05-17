package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSpecificationByEmailPassword implements Specification<User> {
    private static final Logger logger = LogManager.getLogger();


    private String desiredEmail;
    private String desiredPassword;
    private List<User> userList;

    public UserSpecificationByEmailPassword(String desiredEmail, String desiredPassword) {
        this.desiredEmail = desiredEmail;
        this.desiredPassword = desiredPassword;
    }


    @Override
    public List<User> execute() throws RepositoryException{
        String sql = SQLQuery.DEFINE_USER, sqlRole = SQLQuery.DEFINE_ROLE;

        User user;
        Connection connection = null;
        PreparedStatement statement = null, statementRole = null;
        ResultSet rs = null, rsRole = null;
        String roleId;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, desiredEmail);
            statement.setString(2, desiredPassword);

            rs = statement.executeQuery();
            if (rs.next()) {
                System.out.println("USER_ROLE_ID: " + rs.getInt("user_role_id"));
                String userRoleId = String.valueOf(rs.getInt("user_role_id"));
                String userLogin = rs.getString("login");
                statementRole = connection.prepareStatement(sqlRole);
                statementRole.setString(1, userRoleId);
                rsRole = statementRole.executeQuery();
                if(rsRole.next()){
                    user = new User(Long.valueOf(rs.getInt("user_id")), rs.getString("email"),
                            rs.getString("password"),
                            UserRole.valueOf(rsRole.getString("user_role").toUpperCase()),
                            userLogin
                            );
                    System.out.println("FROM SPECIFICATION: " + user);
                    userList = new ArrayList<>();
                    userList.add(user);
                }

            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException();
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection, statement, rs);
        }
        return  userList;
    }
}