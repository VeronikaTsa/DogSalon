package test.com.tsarova.salon.repository.impl;

import com.tsarova.salon.entity.Question;
import com.tsarova.salon.exception.ConnectionPoolException;
import com.tsarova.salon.exception.RepositoryException;
import com.tsarova.salon.pool.ConnectionPoolImpl;
import com.tsarova.salon.repository.Repository;
import com.tsarova.salon.repository.SQLQuery;
import com.tsarova.salon.repository.impl.QuestionRepository;
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
public class QuestionRepositoryTest {
    private static Logger logger = LogManager.getLogger();

    private Question question;
    private Repository<Question> questionRepository;
    private String sqlLastInsertId;
    private String sqlAddQuestion;

    @BeforeClass
    public void setUp() {
        question = new Question(1L, "New content");
        questionRepository = new QuestionRepository();
        sqlLastInsertId = "SELECT * FROM question WHERE question_id=LAST_INSERT_ID();";
        sqlAddQuestion = SQLQuery.ADD_QUESTION;
    }

    @Test(priority = 1)
    public void addTestPositive() {
        try {
            Assert.assertTrue(questionRepository.add(question), "Question wasn't added");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test(priority = 2)
    public void removeTestPositive() {
        Connection connection;
        PreparedStatement statementAddQuestion, statementLastId;
        try {
            connection = ConnectionPoolImpl.getInstance().getConnection();
            statementAddQuestion = connection.prepareStatement(sqlAddQuestion);
            statementAddQuestion.setString(1, String.valueOf(question.getUserId()));
            statementAddQuestion.setString(2, question.getContent());
            if (statementAddQuestion.executeUpdate() > 0) {
                statementLastId = connection.prepareStatement(sqlLastInsertId);
                ResultSet resultSet = statementLastId.executeQuery();
                if (resultSet.next()) {
                    question.setId(Long.valueOf(resultSet.getString("question_id")));
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.catching(Level.ERROR, e);
        }
        try {
            Assert.assertTrue(questionRepository.remove(question), "Question wasn't removed");
        } catch (RepositoryException e) {
            logger.catching(Level.ERROR, e);
        }
    }


    @Test(expectedExceptions = RepositoryException.class, priority = 3)
    public void addTestNegative() throws RepositoryException {
        questionRepository.add(null);
    }

    @Test(expectedExceptions = RepositoryException.class, priority = 3)
    public void removeTestNegative() throws RepositoryException {
        questionRepository.remove(null);
    }
}