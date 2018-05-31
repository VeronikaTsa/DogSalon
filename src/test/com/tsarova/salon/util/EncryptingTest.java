package test.com.tsarova.salon.util;

import com.tsarova.salon.util.Encrypting;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Veronika Tsarova
 */
public class EncryptingTest {
    private String expectedValue;

    @BeforeClass
    public void setUp() {
        expectedValue = "4124bc0a9335c27f086f24ba207a4912";
    }

    @Test
    public void md5EncryptPositive() {
        String actualValue = Encrypting.md5Encrypt("aa");
        Assert.assertEquals(actualValue, expectedValue, "Actual and expected values are not equal");
    }
}
