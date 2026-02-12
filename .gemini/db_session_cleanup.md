# Database Session Cleanup Implementation

## 🎯 Objective
Execute a SQL query to clear any existing user session for 'qa001' before attempting to log in. This prevents potential login issues related to active sessions.

## 🛠️ Changes Made

### 1. Updated `utilities.DatabaseBackupUtil.java`
- **Added Method**: `executeUpdate(String sql, String... params)`
- **Purpose**: Enables execution of `INSERT`, `UPDATE`, and `DELETE` queries (previously only supported `SELECT`).
- **Implementation**:
  ```java
  public static void executeUpdate(String sql, String... params) throws Exception {
      Connection con = getConnection();
      try (PreparedStatement ps = con.prepareStatement(sql)) {
          // Set parameters...
          ps.executeUpdate();
      }
  }
  ```

### 2. Updated `testCasesForOQProjects.Department_TC.java`
- **Method Modified**: `userLoginBeforeCreate()`
- **Logic Added**:
  1. Sets active DB to `PC_DB_NAME`.
  2. Executes `DELETE FROM public.user_session WHERE user_id=(select id from sys_user where login='qa001')`.
  3. Wrapped in `try-catch` to ensure test continuity even if DB cleanup fails.

## 📝 Code Snippet

```java
@Test(groups = { "userlogin" }, priority = 4)
public void userLoginBeforeCreate() throws Throwable {
    // Clear existing user session from DB to prevent login issues
    try {
        log.info("Cleaning up user session for 'qa001' before login...");
        utilities.DatabaseBackupUtil.setActiveDb(PC_DB_NAME);
        String deleteSessionSql = "DELETE FROM public.user_session WHERE user_id=(select id from sys_user where login='qa001')";
        utilities.DatabaseBackupUtil.executeUpdate(deleteSessionSql);
        log.info("User session cleanup executed successfully.");
    } catch (Exception e) {
        log.warn("Failed to clean up user session from DB: " + e.getMessage());
    }

    // Proceed with login...
}
```

## ✅ Result
The test will now automatically clean up the user session before every login attempt, ensuring a clean state for the test execution.
