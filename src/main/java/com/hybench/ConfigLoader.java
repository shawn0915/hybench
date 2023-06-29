package com.hybench;
/**
 *
 * @time 2023-03-04
 * @version 1.0.0
 * @file ConfigLoader.java
 * @description
 *   Load config and print config when start workload.
 **/
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
    Logger logger = LogManager.getLogger(ConfigLoader.class);
    public static String confFile = null;
    public static Properties prop = new Properties();
    
    private static Set<String> falseValuesSet = new HashSet<>() {{
        add("false");
        add("f");
        add("0");
        add("off");
    }};
    
    public static boolean getBoolean(String key, boolean defaultValue) {
        if (prop == null) {
            return false;
        }
        String value = prop.getProperty(key, defaultValue ? "true" : "fasle").toLowerCase(Locale.ENGLISH);
        return !falseValuesSet.contains(value);
    }

    public void loadConfig(){
        try {
            FileInputStream fis = new FileInputStream(ConfigLoader.confFile);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error("Read configure failed : " + ConfigLoader.confFile,e  );
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("Read configure failed : " + ConfigLoader.confFile,e);
            e.printStackTrace();
        }
    }

    public void printConfig(){
        logger.info("===============configuration==================");
        for(Object k:prop.keySet()){
            logger.info(k.toString() + " = " + prop.getProperty(k.toString()));
        }
        logger.info("===============configuration==================");
        logger.info("");
    }
}
