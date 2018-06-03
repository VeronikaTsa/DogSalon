package test.com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Feedback;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPool;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.repository.impl.FeedbackRepository;
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
public class FeedbackRepositoryTest {
    private static Logger logger = LogManager.getLogger();

    private Feedback feedback;
    private Repository<Feedback> feedbackRepository;
    private String sqlLastInsertId;
    private String sqlAddFeedback;

    @BeforeClass
    public void setUp() {
        feedback = new Feedback(1L, "New content");
        feedbackRepository = new FeedbackRepository();
        sqlLastInsertId = "SELECT * FROM feedback WHERE feedback_id=LAST_INSERT_ID();";
        sqlAddFeedback = SQLQuery.ADD_FEEDBACK;
    }

    @Test(priority = 1)
    public void addTestPositive() {
        try {
            Assert.assertTrue(feedbackRepository.add(feedback), "Feedback wasn't added");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test(priority = 2)
    public void removeTestPositive() {
        Connection connection;
        PreparedStatement statementAddService, statementLastId;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            statementAddService = connection.prepareStatement(sqlAddFeedback);
            statementAddService.setString(1, String.valueOf(feedback.getUserId()));
            statementAddService.setString(2, feedback.getContent());
            if (statementAddService.executeUpdate() > 0) {
                statementLastId = connection.prepareStatement(sqlLastInsertId);
                ResultSet resultSet = statementLastId.executeQuery();
                if (resultSet.next()) {
                    feedback.setId(Long.valueOf(resultSet.getString("feedback_id")));
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        try {
            Assert.assertTrue(feedbackRepository.remove(feedback), "Feedback wasn't removed");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }


    @Test(expectedExceptions = RepositoryException.class, priority = 3)
    public void addTestNegative() throws RepositoryException {
        feedbackRepository.add(null);
    }

    @Test(expectedExceptions = RepositoryException.class, priority = 3)
    public void removeTestNegative() throws RepositoryException {
        feedbackRepository.remove(null);
    }
}
