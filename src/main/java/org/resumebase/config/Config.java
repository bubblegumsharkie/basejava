package org.resumebase.config;

import org.resumebase.storage.SQLStorage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final File CONFIG_FILE = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final SQLStorage storage;

    private Config() {
        try (InputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(inputStream);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SQLStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
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

    public SQLStorage getStorage() {
        return storage;
    }

}
