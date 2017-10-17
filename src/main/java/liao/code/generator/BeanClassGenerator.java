package liao.code.generator;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.ReaderModelUtils;
import liao.utils.WriterCodeUtils;

import java.util.List;

/**
 * Created by ao on 2017/10/12.
 */
public class BeanClassGenerator {
    public static void generatorBean(Table table){
        StringBuilder content = getColDefine(table.getColumnList());
        content.append(getMethodDefine(table.getColumnList()));
        String model = ReaderModelUtils.getModel("PoModel");
        String className = NameUtils.getClassName(table.getTableName());
        model = model.replace("#className#",className);
        model = model.replace("#content#",content);
        String fileName = NameUtils.getPOFileName(table.getTableName());
        WriterCodeUtils.writeCode(fileName,model);
    }
    private static StringBuilder getColDefine(List<Column> colList){
        StringBuilder content = new StringBuilder();
        for(Column col : colList){
            content.append("    private "+ col.getColJavaType() + " " + col.getCamelColName() + ";//"+col.getComment()+System.lineSeparator());
        }
        return content;
    }
    private static StringBuilder getMethodDefine(List<Column> colList){
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

}
