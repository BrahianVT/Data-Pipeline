package consume.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author BrahianVT
 * */
public class ReadPropertyFile {
    private Properties config = new Properties();
    public Properties getProperties(){
        InputStream resourceStream = null;
        try {
            config.load(getClass().getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    return config;
    }
}
