package Utilities;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;

public class Configuration {

    static Configuration instance;
    Config conf;
    private Configuration()
    {
//        conf = ConfigFactory.load("resources/application.conf");
        conf = ConfigFactory.parseFile( new File("C:\\CS474_Projects\\CodeGenerator\\src\\main\\resources\\application.conf"));
    }

    public static Configuration getInstance()
    {
        if(instance==null)
            instance= new Configuration();
        return instance;
    }

    public String getValue(String key) {
        return conf.getString(key);
    }

}
