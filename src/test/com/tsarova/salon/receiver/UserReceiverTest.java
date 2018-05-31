package test.com.tsarova.salon.receiver;

import com.tsarova.salon.entity.User;
import com.tsarova.salon.exception.ReceiverException;
import com.tsarova.salon.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @author Veronika Tsarova
 */
public class UserReceiverTest {
    private static Logger logger = LogManager.getLogger();

    private String login;
    private String loginNotExist;

    @BeforeClass
    public void setUp() {
        login = "admin";
        loginNotExist = "loginNotExist";
    }

    @Test
    public void canCreateUserTestPositive() {
        try {
            int code = UserReceiver.canCreateUser("emailfree@mail.com", "freeLogin",
                    "password", "password", new HashMap<>());
            Assert.assertTrue(code > 1, "Can't create user");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void getUserInfoTestPositive() {
        try {
            User user = UserReceiver.getUserInfo(login);
            Assert.assertEquals(user.getEmail(), "admin", "Email isn't equal to \"admin\"");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void canCreateUserTestNegative() {
        try {
            int code = UserReceiver.canCreateUser("", "",
                    "password", "", new HashMap<>());
            Assert.assertTrue(code == 0, "Data is correct");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }

    @Test
    public void getUserInfoTestNegative() {
        try {
            User user = UserReceiver.getUserInfo(loginNotExist);
            Assert.assertNull(user, "User isn't null");
        } catch (ReceiverException e) {
            logger.catching(Level.ERROR, e);
        }
    }
}
