package liao.code.generator.back.sql;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.back.factory.Factory;
import liao.code.generator.back.javacode.BeanClassGenerator;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.PropertyUtils;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by ao on 2017/10/13.
 */
@Component
public class SqlGenerator extends AbstractCodeGenerator {
    private static final String CONFIG_FILE = "sqlModel";
    public String replaceModelCode(Table table,String model){
        String selectSql = createSelectSql(table);
        String insertSql = createInsertSql(table);
        String updateSql = createUpdateSql(table);
        model = model.replace("#selectSQL#",selectSql);
        model = model.replace("#selectSQL#",selectSql);
        model = model.replace("#insertSQL#",insertSql);
        return model.replace("#updateSQL#",updateSql);
    }
    public String createSelectSql(Table table){
        StringBuilder sql = new StringBuilder("SELECT" + System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            t."+col.getColName() + " AS " + col.getCamelColName() + ","+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append( System.lineSeparator());
        sql.append("        FROM " + table.getTableName()+ " t" + System.lineSeparator());
        return sql.toString();
    }
    public String createInsertSql(Table table){
        StringBuilder sql = new StringBuilder("INSERT INTO " +  table.getTableName() +"("+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            "+col.getColName()+ ","+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(")"+System.lineSeparator());
        sql.append("        VALUES("+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            #{"+col.getCamelColName()+ "},"+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(")");
        return sql.toString();
    }
    public String createUpdateSql(Table table){
        StringBuilder sql = new StringBuilder("UPDATE " +  table.getTableName() + " SET"+System.lineSeparator());
        for(Column col : table.getColumnList()){
            sql.append("            "+col.getColName()+ "=#{"+col.getCamelColName()+"},"+System.lineSeparator());
        }
        sql = removeLastChar(sql,",");
        sql.append(System.lineSeparator());
        sql.append("        WHERE id=#{id}");
        return sql.toString();
    }

    public StringBuilder removeLastChar(StringBuilder str,String code){
        return new StringBuilder(str.substring(0,str.lastIndexOf(code)));
    }

    public String getFileName(Table table){
        return "sql"+ File.separator+NameUtils.underline2Camel(table.getTableName().replace(PropertyUtils.getConfig("config").getProperty("tablePre"),""))+"_sql.xml";
    }
    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    public static class SqlFactory implements Factory<SqlGenerator> {
        @Override
        public SqlGenerator create() {
            return new SqlGenerator();
        }
    }
}
