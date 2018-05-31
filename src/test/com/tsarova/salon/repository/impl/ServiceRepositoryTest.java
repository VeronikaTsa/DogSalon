package test.com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Service;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.repository.impl.ServiceRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Veronika Tsarova
 */
public class ServiceRepositoryTest {
    private static Logger logger = LogManager.getLogger();

    private Service service;
    private Repository<Service> serviceRepository;
    private String sqlLastInsertId;
    private String sqlAddService;

    @BeforeClass
    public void setUp() {
        service = new Service("New name", "New content", 20);
        serviceRepository = new ServiceRepository();
        sqlLastInsertId = "SELECT * FROM service WHERE service_id=LAST_INSERT_ID();";
        sqlAddService = SQLQuery.ADD_SERVICE;
    }

    @Test(priority = 1)
    public void addTestPositive() {
        try {
            Assert.assertTrue(serviceRepository.add(service), "Service wasn't added");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test(priority = 2)
    public void updateTestPositive() {
        Connection connection;
        PreparedStatement statementAddService, statementLastId;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statementAddService = connection.prepareStatement(sqlAddService);
            statementAddService.setString(1, service.getName());
            statementAddService.setString(2, service.getContent());
            statementAddService.setString(3, String.valueOf(service.getPrice()));
            if (statementAddService.executeUpdate() > 0) {
                statementLastId = connection.prepareStatement(sqlLastInsertId);
                ResultSet resultSet = statementLastId.executeQuery();
                if (resultSet.next()) {
                    service.setId(Long.valueOf(resultSet.getString("service_id")));
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        service.setContent("Updated content");
        try {
            Assert.assertTrue(serviceRepository.update(service), "Service wasn't updated");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test(priority = 3)
    public void removeTestPositive() {
        try {
            Assert.assertTrue(serviceRepository.remove(service), "Service wasn't removed");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test(expectedExceptions = RepositoryException.class, priority = 4)
    public void addTestNegative() throws RepositoryException {
        serviceRepository.add(null);
    }

    @Test(expectedExceptions = RepositoryException.class, priority = 4)
    public void updateTestNegative() throws RepositoryException {
        serviceRepository.remove(null);
    }

    @Test(expectedExceptions = RepositoryException.class, priority = 4)
    public void removeTestNegative() throws RepositoryException {
        serviceRepository.remove(null);
    }
}
