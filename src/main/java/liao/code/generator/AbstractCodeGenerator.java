package liao.code.generator;

import liao.parse.table.model.Table;
import liao.utils.ReaderUtils;
import liao.utils.WriterCodeUtils;

/**
 * Created by ao on 2017/10/25.
 */
public abstract class AbstractCodeGenerator {
    protected abstract String replaceModelCode(Table table, String model);
    protected abstract String getConfFile();
    protected abstract String getFileName(Table table);
    public void generatorCode(Table table) {
        String model = ReaderUtils.getModel(getConfFile());
        model = replaceModelCode(table,model);
        model = model.replaceAll("#className#", table.getClassName());
        model = model.replaceAll("#alias#", table.getAlias());
        String fileName = getFileName(table);
        WriterCodeUtils.writeCode(fileName,model);
    }
}
