package liao.code.back.generator;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

/**
 * Created by ao on 2017/10/24.
 */
public abstract class AbstractClassGenerator extends AbstractCodeGenerator{
    protected String createCode(Table table){
        return "";
    }
    protected abstract String getFileName(Table table);
    protected String replaceModelCode(Table table, String model){
        String content = createCode(table);
        return model.replace("#content#",content);
    }

}
