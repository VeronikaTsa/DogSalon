package com.tsarova.salon.pool;

import com.tsarova.salon.exception.ConnectionPoolException;

import java.sql.*;

public interface ConnectionPool {
    Connection getConnection() throws ConnectionPoolException;
    void close(Connection connection);
    void destroy();
}