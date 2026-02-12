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

    public void assertTrue(boolean condition, String checkType, String fieldName) {

        String passMsg = checkType + " displayed for field: " + fieldName;
        String failMsg = checkType + " missing for field: " + fieldName;

        if (condition) {
            log.info("✅ {}", passMsg);
        } else {
            log.error("❌ {}", failMsg);
        }

        softAssert.assertTrue(condition, failMsg);
    }

    public void assertFalse(boolean condition, String checkType, String fieldName) {

        String passMsg = checkType + " not displayed/empty for field: " + fieldName;
        String failMsg = checkType + " unexpectedly present/filled for field: " + fieldName;

        if (!condition) {
            log.info("✅ {}", passMsg);
        } else {
            log.error("❌ {}", failMsg);
        }

        softAssert.assertFalse(condition, failMsg);
    }

    public void assertEquals(Object actual, Object expected, String elementType, String fieldName) {

        String passMsg = elementType + " matched for: " + fieldName;
        String failMsg = elementType + " mismatch for: " + fieldName;

        if (actual != null && actual.equals(expected)) {
            log.info("✅ {} [Value: {}]", passMsg, actual);
        } else {
            log.error("❌ {} [Expected: {}, Actual: {}]", failMsg, expected, actual);
        }

        softAssert.assertEquals(actual, expected, failMsg);
    }

    public void assertAll() {
        try {
            softAssert.assertAll();
            log.info("✅ All assertions passed successfully!");
        } catch (AssertionError e) {
            // Print detailed error header
            log.error("❌❌❌ ASSERTION FAILURES DETECTED ❌❌❌");
            log.error("═══════════════════════════════════════════════════════");

            // Print the full error message
            String errorMessage = e.getMessage();
            if (errorMessage != null && !errorMessage.isEmpty()) {
                // Split by newlines and print each line separately for better readability
                String[] errorLines = errorMessage.split("\n");
                for (String line : errorLines) {
                    log.error(line);
                }
            }

            log.error("═══════════════════════════════════════════════════════");

            // Also print to System.out and System.err for Eclipse console visibility
            System.err.println("\n❌❌❌ ASSERTION FAILURES DETECTED ❌❌❌");
            System.err.println("═══════════════════════════════════════════════════════");
            System.err.println(errorMessage);
            System.err.println("═══════════════════════════════════════════════════════\n");

            throw e; // Re-throw so TestNG still marks test as FAIL
        }
    }

    public void assertNotNull(Object object, String elementType, String fieldName) {

        String passMsg = elementType + " exists for: " + fieldName;
        String failMsg = elementType + " missing for: " + fieldName;

        if (object != null) {
            log.info("✅ {}", passMsg);
        } else {
            log.error("❌ {}", failMsg);
        }

        softAssert.assertNotNull(object, failMsg);
    }

    public void assertFail(String message) {
        log.error("❌ Forcing Failure: {}", message);
        softAssert.fail(message);
    }

}
