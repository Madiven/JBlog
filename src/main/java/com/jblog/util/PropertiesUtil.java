package com.jblog.util;

import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    public static String getProperty(String fileName, String key) throws IOException {
        Properties prop = new Properties();
        InputStream inStream = (new DefaultResourceLoader()).getResource("classpath:" + fileName).getInputStream();
        prop.load(inStream);
        return prop.getProperty(key);
    }
}
