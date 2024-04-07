package src.settings;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Update {
    private static final String FILE_PATH = "settings.properties";
    private static final Logger logger = Logger.getLogger(Update.class.getName());

    public static void main(String[] args) {
        updateProperty("cakeRepository", "binary");
        updateProperty("cakeFilePath", "new_cakes.bin");
    }

    public static void updateProperty(String key, String value) {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream(FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading properties file", e);
        }

        // update the property
        properties.setProperty(key, value);

        try (OutputStream output = new FileOutputStream(FILE_PATH)) {
            properties.store(output, "Updated properties");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error updating properties file", e);
        }
    }
}
