package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DataStorage {
    private static final String FILE_PATH = "src/test/resources/properties/runtimeData.properties";
    private static Properties properties = new Properties();

    /**
     * Saves a key-value pair to the runtimeData.properties file.
     */
    public static void save(String key, String value) {
        try {
            // Load existing properties first
            FileInputStream in = new FileInputStream(FILE_PATH);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            // File might not exist yet, ignore
        }

        properties.setProperty(key, value);

        try (FileOutputStream out = new FileOutputStream(FILE_PATH)) {
            properties.store(out, "Runtime Captured Data");
        } catch (IOException e) {
            System.err.println("❌ Failed to save data to " + FILE_PATH + ": " + e.getMessage());
        }
    }

    /**
     * Retrieves a value from the runtimeData.properties file.
     */
    public static String get(String key) {
        try (FileInputStream in = new FileInputStream(FILE_PATH)) {
            properties.load(in);
            return properties.getProperty(key);
        } catch (IOException e) {
            System.err.println("❌ Failed to read data from " + FILE_PATH + ": " + e.getMessage());
            return null;
        }
    }
}
