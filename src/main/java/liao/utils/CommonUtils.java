package liao.utils;

/**
 * Created by ao on 2017/10/25.
 */
public class CommonUtils {
    public static <T extends EnumValue> T getEnumByValue(Integer value, T[] values){
        if(value == null){
            return null;
        }
        for(T enumValue : values){
            if(enumValue.getValue() == value){
                return enumValue;
            }
        }
        return null;
    }
}
