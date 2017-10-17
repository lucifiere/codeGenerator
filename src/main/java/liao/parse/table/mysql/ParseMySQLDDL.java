package liao.parse.table.mysql;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ao on 2017/10/13.
 */
public class ParseMySQLDDL {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        List<String> ddlSql = new ArrayList<String>();
        while (sc.hasNext()){
            String line = sc.nextLine().trim();
            if(line.isEmpty()){//空行忽略
                continue;
            }
            ddlSql.add(line);
            if(line.substring(0,1).equals(")")){//最后一行
                break;
            }
        }
    }
    public static Table parseDDLSQL(List<String> sqlList){
        String tableName = getTableName(sqlList.get(0));
        Table table = new Table();
        table.setTableName(tableName);
        List<Column> columnList = new ArrayList<Column>();
        table.setColumnList(columnList);
        for(String oneLine : sqlList){
            if(oneLine.substring(0,1).equals("`")){//是字段定义sql
                columnList.add(getOneColumn(oneLine));
            }
        }
        return table;
    }
    private static Column getOneColumn(String oneLine){
        oneLine = oneLine.replaceAll("`|'|,","");
        String[] eles = oneLine.split(" ");
        String colName = eles[0];
        String colType = eles[1];
        String colComment = eles[eles.length-1];
        String camelColName = NameUtils.underline2Camel(colName);//转成驼峰命名
        String colJavaType = sqlTypeToJavaType(colType);
        Column col = new Column();
        col.setColName(colName);
        col.setCamelColName(camelColName);
        col.setColDBType(colType);
        col.setColJavaType(colJavaType);
        col.setComment(colComment);
        return col;
    }
    private static String sqlTypeToJavaType(String sqlType){
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
    private static String getTableName(String firstLine){
        String tableName = firstLine.substring(firstLine.indexOf("`")+1,firstLine.lastIndexOf("`"));
        return tableName;
    }
}
