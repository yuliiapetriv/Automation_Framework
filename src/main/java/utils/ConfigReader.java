package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("❌ Cannot find config.properties file");
            }

            properties.load(input);

            // 🧠 Автоматичне визначення GitHub Actions середовища
            String isCI = System.getenv("GITHUB_ACTIONS");
            if ("true".equalsIgnoreCase(isCI)) {
                System.out.println("🧠 Running in GitHub Actions → forcing headless=true");
                properties.setProperty("headless", "true");
            }

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load config.properties: " + e.getMessage());
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }
}
