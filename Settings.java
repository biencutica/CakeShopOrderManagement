package src.settings;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    private static final String FILE_NAME = "settings.properties";
    private Properties properties;
    private Logger logger = Logger.getLogger(Settings.class.getName());

    public Settings() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(FILE_NAME)) {
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties file", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void updateProperty(String key, String value) {
        properties.setProperty(key, value);

        try (OutputStream output = new FileOutputStream(FILE_NAME)) {
            properties.store(output, "Updated properties");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error updating properties file", e);
        }
    }
}
