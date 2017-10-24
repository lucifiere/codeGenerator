package liao.code.back.generator;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;
import org.springframework.beans.factory.BeanFactory;

import java.io.File;
import java.util.List;

/**
 * Created by ao on 2017/10/12.
 */
public class BeanClassGenerator extends AbstractClassGenerator {
    private static final String CONFIG_FILE = "PoModel";
    protected String createCode(Table table){
        StringBuilder content = createAttr(table);
        content.append(getMethodDefine(table.getColumnList()));
        return content.toString();
    }
    private StringBuilder createAttr(Table table){
        List<Column> colList = table.getColumnList();
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }
    private StringBuilder getMethodDefine(List<Column> colList){
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            String getMethod = NameUtils.getGetterMethodName(col.getCamelColName(),col.getColJavaType());
            String setModel = NameUtils.getSetterMethodName(col.getCamelColName());
            content.append("    public "+ col.getColJavaType() + " " +getMethod + "(){"+System.lineSeparator());
            content.append("        return "+col.getCamelColName()+";"+System.lineSeparator());
            content.append("    }"+System.lineSeparator());
            content.append("    public "+ col.getColJavaType() + " " +setModel + "(" +col.getColJavaType()+" "+ col.getCamelColName()+ "){"+System.lineSeparator());
            content.append("        this."+col.getCamelColName()+" = "+col.getCamelColName()+";"+System.lineSeparator());
            content.append("    }"+System.lineSeparator());
        }
        return content;
    }

    public String getFileName(Table table){
        return  "model"+ File.separator+NameUtils.getClassName(table.getTableName())+".java";
    }

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    static class BeanFactory implements Factory<BeanClassGenerator> {
        @Override
        public BeanClassGenerator create() {
            return new BeanClassGenerator();
        }
    }
}
