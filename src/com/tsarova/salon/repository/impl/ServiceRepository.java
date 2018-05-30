package com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.entity.Service;
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
import java.util.ArrayList;
import java.util.List;

public class ServiceRepository implements Repository<Service> {
    private static Logger logger = LogManager.getLogger();


    @Override
    public boolean add(Service service) throws RepositoryException {
        String sql = SQLQuery.ADD_SERVICE;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, service.getName());
            statement.setString(2, service.getContent());
            statement.setString(3, String.valueOf(service.getPrice()));

            if(statement.executeUpdate()>0){
                return true;
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean remove(Service service) throws RepositoryException {
        String sql = SQLQuery.DELETE_SERVICE;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, service.getId().toString());
            if(statement.executeUpdate()>0){
                return true;
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Service service) throws RepositoryException {
        final String SQL_UPDATE_SERVICE = SQLQuery.UPDATE_SERVICE;
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(SQL_UPDATE_SERVICE);
            statement.setString(1, service.getName());
            statement.setString(2, service.getContent());
            statement.setString(3, service.getPicture());
            statement.setString(4, String.valueOf(service.getPrice()));
            statement.setString(5, service.getId().toString());

            if(statement.executeUpdate()>0){
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
    public Service find(Service service) throws RepositoryException {
        return null;
    }

    @Override
    public List<Service> findAll() throws RepositoryException {
        final String SQL_FIND_ALL_SERVICES = SQLQuery.FIND_SERVICES;
        List<Service> serviceList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();

            statement = connection.prepareStatement(SQL_FIND_ALL_SERVICES);
            resultSet = statement.executeQuery();
            while (!resultSet.isLast()){
                if (resultSet.next()) {
                    String serviceContent =resultSet.getString("content");
                    String serviceName = String.valueOf(resultSet.getString("service_name"));
                    double servicePrice = resultSet.getDouble("price");
                    Long serviceId = Long.valueOf(resultSet.getString("service_id"));
                    String servicePicture = resultSet.getString("picture");
                    serviceList.add(new Service(serviceId, serviceName, serviceContent, servicePrice, servicePicture));//??????????????????
                }
            }

        } catch (SQLException | NullPointerException | ConnectionPoolException e) {
            logger.catching(Level.ERROR, e);
            throw new RepositoryException(e);
        } finally {
            ConnectionPoolImpl.getInstance().closeConnection(connection);//лажа
        }
        return serviceList;
    }

    @Override
    public List<Service> query(Specification<Service> specification) throws RepositoryException {
        return null;
    }
}
