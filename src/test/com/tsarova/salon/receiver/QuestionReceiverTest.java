package test.com.tsarova.salon.receiver;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.receiver.QuestionReceiver;
import com.tsarova.salon.repository.SQLQuery;
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
public class QuestionReceiverTest {
    private static Logger logger = LogManager.getLogger();

    private User user;
    private String sqlLastInsertId;
    private String sqlAddQuestion;

    @BeforeClass
    public void setUp() {
        user = new User(1L);
        sqlLastInsertId = "SELECT * FROM question WHERE question_id=LAST_INSERT_ID();";
        sqlAddQuestion = SQLQuery.ADD_QUESTION;
    }

    @Test
    public void addAnswerTestPositive() {
        try {
            Assert.assertTrue(QuestionReceiver.addQuestion("New content", user),
                    "Question wasn't added");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void removeAnswerTestPositive() {
        Long lastInsertId;
        Connection connection;
        PreparedStatement statementAddQuestion, statementLastId;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statementAddQuestion = connection.prepareStatement(sqlAddQuestion);
            statementAddQuestion.setString(1, String.valueOf(user.getUserId()));
            statementAddQuestion.setString(2, "New content");
            if (statementAddQuestion.executeUpdate() > 0) {
                statementLastId = connection.prepareStatement(sqlLastInsertId);
                ResultSet resultSet = statementLastId.executeQuery();
                if (resultSet.next()) {
                    lastInsertId = Long.valueOf(resultSet.getString("question_id"));
                    Assert.assertTrue(QuestionReceiver.removeQuestion(lastInsertId),
                            "Question wasn't removed");
                }
            }
        } catch (ConnectionPoolException | SQLException | ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void addAnswerTestNegative() {
        try {
            Assert.assertFalse(QuestionReceiver.addQuestion("", user),
                    "Question was added");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void removeAnswerTestNegative() {
        try {
            Assert.assertFalse(QuestionReceiver.removeQuestion(0L),
                    "Question was removed");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }
}
