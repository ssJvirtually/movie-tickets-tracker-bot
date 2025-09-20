package in.ssjvirtually.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProps {

    public static Properties readPropertiesFromClasspath(String fileName) {
        Properties properties = new Properties();
        try (InputStream inputStream = ConfigProps.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new IllegalArgumentException("File not found in classpath: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading properties file: " + fileName, e);
        }
        return properties;
    }
}
