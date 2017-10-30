package liao.parse.table.mysql;

import liao.code.generator.page.enums.NullableEnum;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.NameUtils;
import liao.utils.PropertyUtils;

import java.sql.*;
import java.util.*;

/**
 * Created by ao on 2017/10/16.
 */
public class ParseTableForMySQL {
    private static final Properties dbConf = PropertyUtils.getConfig("config");
    private String tableDefineSQL = "select column_name, column_comment,is_nullable,data_type from information_schema.columns where table_schema ='test' and table_name = '#tableName#';";
    private Connection conn;
    private Table table;
    public ParseTableForMySQL(String tableName){
        table = new Table(tableName);
    }
    public Table getTable(){
        tableDefineSQL = tableDefineSQL.replace("#tableName#",table.getTableName());
        try {
            conn = getConnection();
            Statement stat = getStatement(conn);
            ResultSet rs = stat.executeQuery(tableDefineSQL);
            List<Column> columnList = convertToColumnList(rs);
            table.setColumnList(columnList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return table;
    }
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dbConf.getProperty("url"),dbConf.getProperty("name"),dbConf.getProperty("password"));
    }
    public Statement getStatement(Connection conn) throws SQLException {
        return conn.createStatement();
    }
    private List<Column> convertToColumnList(ResultSet rs) throws SQLException {
        List<Column> columnList = new ArrayList<>();
        while(rs.next()){
            String colName = rs.getString("column_name");
            String colType = rs.getString("data_type");
            String colComment = rs.getString("column_comment");
            int isNullable = rs.getBoolean("is_nullable") ? NullableEnum.YES.getValue() : NullableEnum.NO.getValue();
            String camelColName = NameUtils.underline2Camel(colName);//转成驼峰命名
            String colJavaType = CommonUtils.sqlTypeToJavaType(colType);
            Column col = new Column();
            col.setColName(colName);
            col.setCamelColName(camelColName);
            col.setColDBType(colType);
            col.setColJavaType(colJavaType);
            col.setComment(colComment);
            col.setNullable(isNullable);
            columnList.add(col);
        }
        return columnList;
    }
}
