package hospital.database;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Loads database settings from an external properties file. */
public final class ApplicationConfig {
    private static final String FILE_NAME = "properties.properties";
    private static final Properties VALUES = load();

    private ApplicationConfig() { }

    public static String get(String key, String fallback) {
        String value = VALUES.getProperty(key);
        return value == null || value.isBlank() ? fallback : value.trim();
    }

    private static Properties load() {
        Properties values = new Properties();
        Path external = Path.of(FILE_NAME);
        try {
            if (Files.exists(external)) {
                try (InputStream input = Files.newInputStream(external)) {
                    values.load(input);
                    return values;
                }
            }
        } catch (IOException ignored) { }
        try (InputStream input = ApplicationConfig.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (input != null) values.load(input);
        } catch (IOException ignored) { }
        return values;
    }
}
