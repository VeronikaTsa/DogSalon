package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.entity.UserSex;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserContent;
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
import java.util.List;

public class UserRepository implements Repository<User> {
    private static final Logger logger = LogManager.getLogger();

    private Connection connectionR;

    @Override
    public boolean add(User user) {
        return false;
    }

    @Override
    public boolean remove(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> query(Specification<User> specification) throws RepositoryException{
        return specification.execute();
    }

    @Override
    public User find(User user) throws RepositoryException {
        String sqlUser = SQLQuery.DEFINE_USER;
        Connection connection = null;
        PreparedStatement statementUser = null;
        ResultSet resultSetUser = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statementUser = connection.prepareStatement(sqlUser);
            statementUser.setString(1, user.getEmail());
            statementUser.setString(2, user.getPassword());
            System.out.println("ищем с мэил и паролем: " + user.getEmail() + " " + user.getPassword());
            resultSetUser = statementUser.executeQuery();
            if (resultSetUser.next()) {
                System.out.println("нашли");
                user.setRole(UserRole.valueOf(resultSetUser.getString("user_role").toUpperCase()));
                user.setUserId(Long.valueOf(resultSetUser.getString("user_id")));
                user.setLogin(resultSetUser.getString("login"));

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

                return user;
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);//тоже в трай? если подключеня нет, попробует закрыть на налле
        }
        return null;        //????????????????
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        return null;
    }


    /*@Override
    public User find(User user) throws RepositoryException {
        String sql = SQLQuery.DEFINE_USER;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String roleId;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            rs = statement.executeQuery();
            if (rs.next()) {
                roleId = rs.getString("user_role_Id");
                //user.setRole(role);
                return new User();
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            //logger.fatal(e);
            e.printStackTrace();
            throw new RepositoryException();
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection, statement, rs);
        }
        return new User();
    }*/
}
