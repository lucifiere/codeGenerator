package liao.code.generator.page.generator;

import liao.code.generator.AbstractCodeGenerator;
import liao.parse.table.model.Table;

/**
 * Created by ao on 2017/10/25.
 */
public class JspGenerator extends AbstractCodeGenerator{
    @Override
    protected String replaceModelCode(Table table, String model) {
        return null;
    }

    @Override
    protected String getConfFile() {
        return null;
    }

    @Override
    protected String getFileName(Table table) {
        return null;
    }
}
