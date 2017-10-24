package liao.code.back.generator;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.PropertyUtils;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

import java.io.File;
import java.util.Properties;

/**
 * Created by ao on 2017/10/13.
 */
public class SqlGenerator {
    public static void generatorSQL(Table table){
        String selectSql = createSelectSql(table);
        String insertSql = createInsertSql(table);
        String updateSql = createUpdateSql(table);
        String alias = NameUtils.getAliasName(table.getTableName());
        String className = NameUtils.getClassName(table.getTableName());
        String fileName = getSqlFileName(table.getTableName());
        String model = ReaderUtils.getModel("sqlModel");
        model = model.replace("#className#",className);
        model = model.replace("#alias#",alias);
        model = model.replace("#selectSQL#",selectSql);
        model = model.replace("#insertSQL#",insertSql);
        model = model.replace("#updateSQL#",updateSql);
        WriterCodeUtils.writeCode(fileName,model);

    }
    public static String createSelectSql(Table table){
        StringBuilder sql = new StringBuilder("SELECT" + System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            t."+col.getColName() + " AS " + col.getCamelColName() + ","+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append( System.lineSeparator());
        sql.append("        FROM " + table.getTableName()+ " t" + System.lineSeparator());
        return sql.toString();
    }
    public static String createInsertSql(Table table){
        StringBuilder sql = new StringBuilder("INSERT INTO " +  table.getTableName() +"("+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            "+col.getColName()+ ","+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(")"+System.lineSeparator());
        sql.append("        VALUES("+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            #"+col.getCamelColName()+ "#,"+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(")");
        return sql.toString();
    }
    public static String createUpdateSql(Table table){
        StringBuilder sql = new StringBuilder("UPDATE " +  table.getTableName() + " SET"+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            "+col.getColName()+ "=#"+col.getCamelColName()+"#,"+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(System.lineSeparator());
        sql.append("        WHERE id=#id#");
        return sql.toString();
    }

    public static StringBuilder removeLastChar(StringBuilder str,String code){
        return new StringBuilder(str.substring(0,str.lastIndexOf(code)));
    }

    public static String getSqlFileName(String tableName){
        return "sql"+ File.separator+NameUtils.underline2Camel(tableName.replace(PropertyUtils.getConfig("config").getProperty("tablePre"),""))+"_sql.xml";
    }

}
