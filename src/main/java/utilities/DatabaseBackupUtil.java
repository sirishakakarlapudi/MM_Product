package utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseBackupUtil {
    private static final Logger log = LogManager.getLogger(DatabaseBackupUtil.class);

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
                    "set PGPASSWORD=" + dbPass + "&& \"" + pgDumpPath + "\" -Fc -U " + dbUser + " -h " + host + " -p " + port + " -f "
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
}
