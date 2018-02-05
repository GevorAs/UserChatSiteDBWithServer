package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadPathImages {
    public String loadString() throws IOException {
        final Properties properties = new Properties();
        InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
        properties.load(inputStream);
      String  path=properties.getProperty("image.dir");
        return path;
    }
}
