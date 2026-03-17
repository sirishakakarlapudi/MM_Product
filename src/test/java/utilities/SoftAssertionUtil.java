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

            // Split and clean the error message
            String errorMessage = e.getMessage();
            if (errorMessage != null && !errorMessage.isEmpty()) {
                String[] rawErrors = errorMessage.split("The following asserts failed:");
                String actualErrors = rawErrors.length > 1 ? rawErrors[1] : errorMessage;

                String[] errorLines = actualErrors.split("\t");
                int failureCount = 0;
                for (String line : errorLines) {
                    if (line.trim().isEmpty())
                        continue;
                    failureCount++;
                    log.error("Failure {}: {}", failureCount, line.trim());
                }
            }

            log.error("═══════════════════════════════════════════════════════");

            // Also print to System.err for console visibility (interleaving fixed by using
            // unified log first)
            String header = "❌❌❌ " + errorMessage.split("\n")[0] + " ❌❌❌";
            log.error(header);
            System.err.println("\n" + header);
            System.err.println(errorMessage);

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
