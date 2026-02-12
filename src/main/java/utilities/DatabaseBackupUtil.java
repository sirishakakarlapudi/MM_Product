package utilities;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseBackupUtil {
    private static final Logger log = LogManager.getLogger(DatabaseBackupUtil.class);
    private static String activeDbName;
    private static Connection connection;

    public static void backupPostgres(String scriptNumber, String dbName, String dbUser, String dbPass, String host,
            String port) {
        try {
            log.info("--- Starting Database Backup for: {} (Script: {}) ---", dbName, scriptNumber);

            // 1. Prepare timestamped filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = dbName + "_backup_" + timestamp + ".dump";

            // 2. Prepare backup directory
            String projectPath = System.getProperty("user.dir");
            String backupDirPath = projectPath + File.separator + "src" + File.separator + "test" + File.separator
                    + "resources" + File.separator + "DB_Backups" + File.separator + scriptNumber;
            File backupDir = new File(backupDirPath);
            if (!backupDir.exists()) {
                backupDir.mkdirs();
                log.info("Created backup subdirectory: {}", backupDirPath);
            }

            String fullPath = backupDirPath + File.separator + fileName;

            // 3. Build pg_dump command with Custom Format (-Fc)
            // Using absolute path for PostgreSQL 17
            String pgDumpPath = "C:\\Program Files\\PostgreSQL\\17\\bin\\pg_dump.exe";
            ProcessBuilder pb = new ProcessBuilder(
                    "cmd", "/c",
                    "set PGPASSWORD=" + dbPass + "&& \"" + pgDumpPath + "\" -Fc -U " + dbUser + " -h " + host + " -p "
                            + port + " -f "
                            + "\"" + fullPath + "\" " + dbName);

            log.info("Executing backup command: {}", pb.command());
            Process process = pb.start();

            // Capture error stream
            String errorResult = "";
            try (Scanner s = new Scanner(process.getErrorStream()).useDelimiter("\\A")) {
                errorResult = s.hasNext() ? s.next() : "";
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("✅ Database backup successful! File saved at: {}", fullPath);
            } else {
                log.error("❌ Database backup failed with exit code: {}", exitCode);
                log.error("Command Error Output: {}", errorResult);
            }

        } catch (Exception e) {
            log.error("❌ Exception during database backup: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static void setActiveDb(String dbName) {
        activeDbName = dbName;
        connection = null; // force reconnect if DB changes
    }

    public static Connection getConnection() throws Exception {

        if (connection == null || connection.isClosed()) {

            if (activeDbName == null) {
                throw new RuntimeException("❌ Active DB is not set. Call setActiveDb(dbName) first.");
            }

            String url = "jdbc:postgresql://localhost:5432/" + activeDbName;

            log.info("Connecting to PostgreSQL DB: {}", url);

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    url,
                    "postgres",
                    "root");
        }

        return connection;
    }

    public static boolean hasAuthority(String username, String authority) throws Exception {
        Connection con = getConnection();
        String sql = "SELECT COUNT(*) " +
                "FROM sys_user_authority " +
                "WHERE user_id = (SELECT id FROM sys_user WHERE login = ?) " +
                "AND authority_name = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, authority);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public static boolean hasAnyAuthorityLike(String username, String authorityPattern) throws Exception {
        Connection con = getConnection();
        // authorityPattern should be like 'DEPARTMENT%' or 'BILL_OF_MATERIAL%'
        String sql = "SELECT COUNT(*) " +
                "FROM sys_user_authority " +
                "WHERE user_id = (SELECT id FROM sys_user WHERE login = ?) " +
                "AND authority_name LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, authorityPattern);

            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public static String getSingleValueFromDB(String sql, String... params) throws Exception {
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(1);
                }
            }
        }
        return "NA";
    }

    public static List<String> getMultipleValuesFromDB(String sql, String... params) throws Exception {
        Connection con = getConnection();
        List<String> results = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(rs.getString(1));
                }
            }
        }
        return results;
    }

    public static List<java.util.Map<String, String>> getRowsFromDB(String sql, String... params) throws Exception {
        Connection con = getConnection();
        List<java.util.Map<String, String>> results = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                java.sql.ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (rs.next()) {
                    java.util.Map<String, String> row = new java.util.LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i);
                        String value = rs.getString(i);
                        row.put(columnName, value != null ? value : "");
                    }
                    results.add(row);
                }
            }
        }
        return results;
    }

    public static void executeUpdate(String sql, String... params) throws Exception {
        Connection con = getConnection();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setString(i + 1, params[i]);
            }
            int rowsAffected = ps.executeUpdate();
            log.info("Executed update/delete. Rows affected: {}", rowsAffected);
        }
    }
}
