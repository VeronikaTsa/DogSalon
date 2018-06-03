package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.UserRole;
import com.tsarova.salon.entity.UserSex;
import com.tsarova.salon.entity.User;
import com.tsarova.salon.entity.UserContent;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.specification.Specification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * @author Veronika Tsarova
 */
public class UserRepository implements Repository<User> {
    private static Logger logger = LogManager.getLogger();

    @Override
    public boolean add(User user) throws RepositoryException {
        String SQL_CREATE_USER = SQLQuery.CREATE_USER;
        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_CREATE_USER);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getRole().getValue());

            if (statement.executeUpdate() > 0) {
                String SQL_DEFINE_USER_ID = SQLQuery.DEFINE_USER_ID;
                PreparedStatement statementUserId;
                ResultSet resultSetUserId;
                statementUserId = connection.prepareStatement(SQL_DEFINE_USER_ID);
                resultSetUserId = statementUserId.executeQuery();
                if (resultSetUserId.next()) {
                    user.setUserId(Long.valueOf(resultSetUserId.getString("id")));
                    return true;
                }
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException();
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public boolean remove(User user) {
        return false;
    }

    @Override
    public boolean update(User user) throws RepositoryException {
        final String SQL_UPDATE_USER = SQLQuery.UPDATE_USER;
        final String SQL_REPLACE_USER_CONTENT = SQLQuery.REPLACE_USER_CONTENT;
        final String SQL_DELETE_USER_CONTENT = SQLQuery.DELETE_USER_CONTENT;

        Connection connection = null;
        PreparedStatement statement;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, String.valueOf(user.getUserId()));

            if (statement.executeUpdate() > 0) {
                if (user.getUserContent() != null) {
                    PreparedStatement statementUserInfo;
                    statementUserInfo = connection.prepareStatement(SQL_REPLACE_USER_CONTENT);
                    statementUserInfo.setString(1, user.getUserContent().getFirstName());
                    statementUserInfo.setString(2, user.getUserContent().getLastName());
                    statementUserInfo.setString(3, user.getUserContent().getTelephone());
                    statementUserInfo.setString(6, String.valueOf(user.getUserId()));
                    if (user.getUserContent().getSex() != null) {
                        statementUserInfo.setString(5, String.valueOf(user.getUserContent().getSex()));
                    } else {
                        statementUserInfo.setString(5, null);
                    }
                    if (user.getUserContent().getBirthday() != null) {
                        statementUserInfo.setString(4, String.valueOf(user.getUserContent().getBirthday()));
                    } else {
                        statementUserInfo.setString(4, null);
                    }
                    if (statementUserInfo.executeUpdate() > 0) {
                        logger.log(Level.DEBUG, "Content was updated");
                        return true;
                    }
                } else {
                    PreparedStatement statementUserInfoDel;
                    statementUserInfoDel = connection.prepareStatement(SQL_DELETE_USER_CONTENT);
                    statementUserInfoDel.setString(1, String.valueOf(user.getUserId()));
                    if (statementUserInfoDel.executeUpdate() > 0) {
                        logger.log(Level.DEBUG, "Content was deleted");
                        return true;
                    }
                }
                return true;
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return false;
    }

    @Override
    public List<User> query(Specification<User> specification) throws RepositoryException {
        return specification.execute();
    }

    @Override
    public User find(User user) throws RepositoryException {
        final String SQL_DEFINE_USER = SQLQuery.DEFINE_USER;
        Connection connection = null;
        PreparedStatement statementUser;
        ResultSet resultSetUser;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            statementUser = connection.prepareStatement(SQL_DEFINE_USER);
            statementUser.setString(1, user.getEmail());
            statementUser.setString(2, user.getPassword());
            resultSetUser = statementUser.executeQuery();

            if (resultSetUser.next()) {
                logger.log(Level.DEBUG, "Found user " + user.getEmail() + " in database");
                user.setRole(UserRole.valueOf(resultSetUser.getString("user_role").toUpperCase()));
                user.setUserId(Long.valueOf(resultSetUser.getString("user_id")));
                user.setLogin(resultSetUser.getString("login"));

                PreparedStatement statementUserContent;
                ResultSet resultSetUserContent;
                final String SQL_DEFINE_USER_CONTENT = SQLQuery.DEFINE_USER_CONTENT;
                statementUserContent = connection.prepareStatement(SQL_DEFINE_USER_CONTENT);
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
                return user;
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if (connection != null) {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public List<User> findAll() throws RepositoryException {
        return null;
    }
}
