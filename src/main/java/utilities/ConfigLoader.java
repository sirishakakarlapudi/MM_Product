package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigLoader {

    private final Properties properties = new Properties();
    private static final String BASE_PATH = "src/test/resources/properties/";

    public ConfigLoader(String fileName) {
        try {
            File file = findFile(new File(BASE_PATH), fileName);
            if (file != null && file.exists()) {
                try (InputStream is = new FileInputStream(file)) {
                    properties.load(is);
                }
            } else {
                // Fallback for paths that already include the subfolder index (e.g.
                // "Department/login.properties")
                File fallbackFile = new File(BASE_PATH + fileName);
                if (fallbackFile.exists()) {
                    try (InputStream is = new FileInputStream(fallbackFile)) {
                        properties.load(is);
                    }
                } else {
                    throw new IOException(
                            "❌ Property file not found: '" + fileName + "' in " + BASE_PATH + " or subfolders.");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration: " + e.getMessage(), e);
        }
    }

    private File findFile(File dir, String fileName) {
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }

        // 1. Check in the current directory (only match files)
        File targetFile = new File(dir, fileName);
        if (targetFile.exists() && targetFile.isFile()) {
            return targetFile;
        }

        // 2. Recursively search in subdirectories
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File found = findFile(file, fileName);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            return null;
        }
        value = value.trim();
        if (value.contains("${")) {
            return resolvePlaceholders(value).trim();
        }
        return value;
    }

    private String resolvePlaceholders(String value) {
        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String placeholderKey = matcher.group(1);
            String replacement = properties.getProperty(placeholderKey, matcher.group(0));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
