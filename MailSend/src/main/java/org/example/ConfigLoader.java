package org.example;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {
    public static Properties load(String filePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (Exception e) {
            System.out.println("Failed to load config: " + e.getMessage());
        }
        return props;
    }
}