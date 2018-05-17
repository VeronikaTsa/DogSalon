package com.tsarova.salon.pool;

import com.tsarova.salon.exception.ConnectionPoolException;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;

public interface ConnectionPool {
    Connection getConnection() throws ConnectionPoolException;
    void close(Connection connection);
    void destroy();
}