package com.tsarova.salon.pool;

import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.resource.db.DBParameter;
import com.tsarova.salon.resource.db.DBResourceManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolImpl {
    private static Logger logger = LogManager.getLogger();

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    private BlockingQueue<Connection> connectionQueue;
    private ArrayDeque<Connection> runningConnectionQueue;

    private static final int DEFAULT_POOL_SIZE = 10;
    private static ReentrantLock lockingInstance = new ReentrantLock();
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ConnectionPoolImpl instance;

    public static ConnectionPoolImpl getInstance() {
        if (!instanceCreated.get() || instance == null) {
            lockingInstance.lock();
            try {
                if (instanceCreated.compareAndSet(false, true)) {
                    instance = new ConnectionPoolImpl();
                }
            } finally {
                lockingInstance.unlock();
            }
        }
        return instance;
    }

    private ConnectionPoolImpl() {
        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        this.url = dbResourceManager.getValue(DBParameter.DB_URL);
        this.user = dbResourceManager.getValue(DBParameter.DB_USER);
        this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
        try {
            this.poolSize = Integer.parseInt(dbResourceManager
                    .getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            this.poolSize = DEFAULT_POOL_SIZE;
        }
        try {
            initPoolData();
        } catch (ConnectionPoolException e) {
            logger.log(Level.ERROR, "Error initializing connection pool", e);
            e.printStackTrace();
        }
    }

    private void initPoolData() throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
            runningConnectionQueue = new ArrayDeque<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user,
                        password);
                ProxyConnection proxyConnection = new ProxyConnection(
                        connection);
                connectionQueue.add(proxyConnection);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionPoolException();
        }
    }

    @SuppressWarnings("null")
    private void closeConnectionsQueue(BlockingQueue<Connection> queue)
            throws SQLException {
        Connection connection = queue.poll();
        while (connection != null) {
            connection.commit();
            connection = queue.poll();
        }
        ((ProxyConnection) connection).closeConnection();
    }

    public void dispose() {
        try {
            //closeConnectionsQueue(runningConnectionQueue);//наверное, не нужно
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Error closing connection", e);
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
            runningConnectionQueue.offer(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(
                    "Error connecting to the data source", e);
        }

        return connection;
    }

    public void closeConnection(Connection con, Statement st,
                                ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "ResultSet isn't closed");
            e.printStackTrace();
        }

        try {
            st.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement isn't closed");
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection con, Statement st) {
        try {
            st.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Statement isn't closed");
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
            e.printStackTrace();
        }
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Connection isn't return to the pool");
            e.printStackTrace();
        }
    }

    private class ProxyConnection implements Connection {
        private Connection connection;

        ProxyConnection(Connection con) throws SQLException {
            this.connection = con;
            this.connection.setAutoCommit(true);
        }

        public void closeConnection() throws SQLException {
            connection.close();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection");
            }

            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }

            if (!runningConnectionQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the "
                        + "given away connection pool");
            }

            if (!connectionQueue.offer(this)) {
                throw new SQLException(
                        "Error allocating connection in the pool");
            }

        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements)
                throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public Statement createStatement(int resultSetType,
                                         int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType,
                    resultSetConcurrency);
        }

        @Override
        public Statement createStatement(int resultSetType,
                                         int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.createStatement(resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes)
                throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType,
                                             int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType,
                    resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType,
                                             int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {
            return connection.prepareCall(sql, resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql)
                throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int resultSetType, int resultSetConcurrency)
                throws SQLException {
            return connection.prepareStatement(sql, resultSetType,
                    resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql,
                                                  int resultSetType, int resultSetConcurrency,
                                                  int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType,
                    resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);

        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();

        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);

        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);

        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);

        }

        @Override
        public void setClientInfo(Properties properties)
                throws SQLClientInfoException {
            connection.setClientInfo(properties);

        }

        @Override
        public void setClientInfo(String name, String value)
                throws SQLClientInfoException {
            connection.setClientInfo(name, value);

        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);

        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds)
                throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);

        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);

        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {

            return connection.setSavepoint(name);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);

        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);

        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);

        }

    }
}
