package liao.utils;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim())){
            return true;
        }
        return false;
    }
    public static  Map<Integer,String> convertEnumToMap(EnumValue[] values,boolean hasAll){
        LinkedHashMap<Integer,String> map = new LinkedHashMap<>();

        for(EnumValue enumValue : values){
            map.put(enumValue.getValue(),enumValue.getDesc());
        }
        return map;
    }

    public static  Map<Integer,String> convertEnumToMap(EnumValue[] values){
        LinkedHashMap<Integer,String> map = new LinkedHashMap<>();
        for(EnumValue enumValue : values){
           map.put(enumValue.getValue(),enumValue.getDesc());
        }
        return map;
    }

    public static String sqlTypeToJavaType(String sqlType){
        sqlType = sqlType.replaceAll("\\(.+\\)","").toUpperCase();//去掉括号
        if(sqlType.equals("BIGINT")){
            return "Long";
        }else if(sqlType.equals("INT") || sqlType.equals("SMALLINT") || sqlType.equals("TINYINT")){
            return "Integer";
        }else if (sqlType.equals("CHAR") || sqlType.equals("VARCHAR")){
            return "String";
        }else if(sqlType.equals("DATE") || sqlType.equals("DATETIME")){
            return "Date";
        }else if(sqlType.equals("DECIMAL")){
            return "BigDecimal";
        }
        return sqlType;
    }
}
