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
    	} catch (AssertionError e) {
    	   
    	    log.error(e.getMessage());   // This prints your exact error
    	    throw e;                     // Re-throw so TestNG still marks test as FAIL
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


}
