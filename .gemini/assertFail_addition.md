# Added assertFail Method

## 🎯 Objective
Added `assertFail(String message)` to `SoftAssertionUtil.java` to support explicit test failures with logging.

## 🛠️ Implementation
```java
public void assertFail(String message) {
    log.error("❌ Forcing Failure: {}", message);
    softAssert.fail(message);
}
```

## ⚠️ Important Note regarding Build Errors
I noticed unrelated compilation errors in `SecurityGroup_TC.java` and `Login_TC.java`. These are caused by recent changes to `login()` and `switchUser()` signatures in `BasePage.java` (requiring `dbName`), which have not yet been propagated to these other test classes. You may want to address these soon.
