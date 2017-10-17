package liao.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ao on 2017/10/16.
 */
public class ReaderModelUtils {
    public static String getModel(String modelName){
        try {
            return  readAllModelLines(modelName);
        } catch (IOException e) {
            throw new RuntimeException(modelName+"文件不存在");
        }
    }
    private static String readAllModelLines(String modelName) throws IOException {
        StringBuilder modelText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ReaderModelUtils.class.getClassLoader().getResourceAsStream("model/" + modelName + ".txt")))) {
            String line = null;
            while((line = reader.readLine()) != null){
                modelText.append(line + System.lineSeparator());
            }
        }
        return modelText.toString();
    }
}
