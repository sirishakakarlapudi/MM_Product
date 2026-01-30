package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {
    private SoftAssert softAssert;
    private Logger log = LogManager.getLogger(this.getClass());

    public SoftAssertionUtil() {
        softAssert = new SoftAssert();
    }

    public void assertTrue(boolean condition, String message) {
        if (condition) {
            log.info("✅ Assertion Passed: {}", message);
        } else {
            log.error("❌ Assertion Failed: {}", message);
        }
        softAssert.assertTrue(condition, message);
    }

    public void assertEquals(Object actual, Object expected, String message) {
        if (String.valueOf(actual).equals(String.valueOf(expected))) {
            log.info("✅ Assertion Passed: {} [Value: {}]", message, actual);
        } else {
            log.error("❌ Assertion Failed: {} [Expected: {}, Actual: {}]", message, expected, actual);
        }
        softAssert.assertEquals(actual, expected, message);
    }

    public void assertAll() {
        softAssert.assertAll();
    }
}
