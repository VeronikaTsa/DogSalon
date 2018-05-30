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
    private static Logger logger = LogManager.getLogger();

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
    public boolean update(User user) throws RepositoryException {
        final String SQL_UPDATE_USER = SQLQuery.UPDATE_USER;
        final String SQL_UPDATE_USER_INFO = SQLQuery.UPDATE_USER_INFO;

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, String.valueOf(user.getUserId()));

            if(statement.executeUpdate()>0){
                if(user.getUserContent() != null){
                    PreparedStatement statementUserInfo;
                    statementUserInfo = connection.prepareStatement(SQL_UPDATE_USER_INFO);
                    statementUserInfo.setString(1, user.getUserContent().getFirstName());
                    statementUserInfo.setString(2, user.getUserContent().getLastName());
                    statementUserInfo.setString(3, user.getUserContent().getTelephone());
                    statementUserInfo.setString(6, String.valueOf(user.getUserId()));
                    if(user.getUserContent().getSex() != null){
                        statementUserInfo.setString(5, String.valueOf(user.getUserContent().getSex()));
                    } else {
                        statementUserInfo.setString(5, null);
                    }
                    if(user.getUserContent().getBirthday() != null){
                        statementUserInfo.setString(4, String.valueOf(user.getUserContent().getBirthday()));
                    } else {
                        statementUserInfo.setString(4, null);
                    }

                    if(statementUserInfo.executeUpdate()>0){
                        return true;//??????????????????
                    }
                }
                return true;
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            if(connection != null)
            {
                ConnectionPoolImpl.getInstance().closeConnection(connection);
            }
        }
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
                ConnectionPoolImpl.getInstance().closeConnection(connection);
                return user;
            }
        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            //тоже в трай? если подключеня нет, попробует закрыть на налле
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
