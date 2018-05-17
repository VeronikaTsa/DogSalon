package com.tsarova.salon.specification.impl;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
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

public class UserSpecificationByLogin implements Specification<User> {
    private static Logger logger = LogManager.getLogger();


    private String desiredLogin;
    private List<User> userList = new ArrayList<>();

    public UserSpecificationByLogin(String desiredLogin) {
        this.desiredLogin = desiredLogin;
    }

    @Override
    public List<User> execute() throws RepositoryException {
        String sql = SQLQuery.IF_EXIST_LOGIN;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSetUser = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, desiredLogin);
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
