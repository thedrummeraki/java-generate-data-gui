package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class Config {

    public static final String PATH = "config/config.properties";

    private static Config config;
    private static final Object lock = new Object();

    public static Config getInstance() {
        if (config == null) {
            synchronized (lock) {
                if (config == null) {
                    config = new Config(PATH);
                }
            }
        }
        return config;
    }

    private Properties properties;

    private Config(String path) {
        properties = new Properties();
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(path);
            properties.load(fi);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("The config file \"" + path + "\" doesn't exist.");
        } catch (IOException e) {
            throw new IllegalStateException("The config file \"" + path + "\" isn't valid.");
        } finally {
            try {
                if (fi != null)
                    fi.close();
            } catch (IOException e) {
                // silently fail
            }
        }
    }

    public String get(String key) {
        String res = opt(key);
        if (res == null) {
            throw new IllegalArgumentException("The key \"" + key + "\" doesn't exist in this configuration file.");
        }
        return res;
    }

    public String opt(String key) {
        return properties.getProperty(key);
    }

}
