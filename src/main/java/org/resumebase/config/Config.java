package org.resumebase.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final File CONFIG_FILE = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final Properties props = new Properties();
    private final File storageDir ;

    private Config() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid props directory" + CONFIG_FILE.getAbsolutePath());
        }

    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
