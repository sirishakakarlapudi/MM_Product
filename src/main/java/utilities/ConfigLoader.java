package utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties = new Properties();

    // Constructor loads a specific file
    public ConfigLoader(String fileName) {
        try {
            properties.load(new FileReader("src/test/resources/properties/" + fileName));
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load properties file: " + fileName, e);
        }
    }

    // Getter with optional placeholder resolution
    public String get(String key) {
        String value = properties.getProperty(key);
        if (value != null && value.contains("${")) {
            return resolvePlaceholders(value);
        }
        return value;
    }

    private String resolvePlaceholders(String value) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\$\\{([^}]+)\\}");
        java.util.regex.Matcher matcher = pattern.matcher(value);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String placeholderKey = matcher.group(1);
            String replacement = properties.getProperty(placeholderKey, matcher.group(0));
            matcher.appendReplacement(sb, java.util.regex.Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
