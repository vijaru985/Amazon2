package utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    // Load configuration only once
    static {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream("configure.properties")) {

            if (inputStream == null) {

                throw new RuntimeException(
                        "configure.properties not found in classpath"
                );
            }

            PROPERTIES.load(inputStream);

            Log.logger.info(
                    "configure.properties loaded successfully."
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to load configure.properties",
                    e
            );
        }
    }

    // Prevent object creation
    private ConfigReader() {
    }

    /**
     * Returns configuration value.
     */
    public static String getProperty(String key) {

        String value =
                PROPERTIES.getProperty(key);

        if (value == null) {

            throw new RuntimeException(
                    "Property '" + key +
                    "' not found in configure.properties"
            );
        }

        return value.trim();
    }

    /**
     * Returns boolean configuration value.
     */
    public static boolean getBooleanProperty(
            String key) {

        return Boolean.parseBoolean(
                getProperty(key)
        );
    }
}