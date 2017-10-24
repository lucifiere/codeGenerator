package liao.code.back.generator;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

/**
 * Created by ao on 2017/10/24.
 */
public abstract class AbstractClassGenerator {
    protected abstract String getConfFile();
    protected String createCode(Table table){
        return "";
    }
    protected abstract String getFileName(Table table);
    public void generatorCode(Table table) {
        String content = createCode(table);
        String model = ReaderUtils.getModel(getConfFile());
        model = model.replaceAll("#className#", table.getClassName());
        model = model.replaceAll("#alias#", table.getAlias());
        model = model.replace("#content#", content);
        String fileName = getFileName(table);
        WriterCodeUtils.writeCode(fileName,model);
    }
}
