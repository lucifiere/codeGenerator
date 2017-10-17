package liao.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ao on 2017/10/16.
 */
public class PropertyUtils {
    public static Properties getConfig(String fileName){
        Properties pro = new Properties();
        try(InputStream in = PropertyUtils.class.getResourceAsStream("/"+fileName+".properties")) {
            pro.load(in);
            return pro;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
