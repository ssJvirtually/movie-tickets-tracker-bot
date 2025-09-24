package in.ssjvirtually.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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

    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = ConfigProps.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in classpath: " + fileName);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
