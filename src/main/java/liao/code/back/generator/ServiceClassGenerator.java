package liao.code.back.generator;

import liao.parse.table.model.Table;
import liao.utils.NameUtils;

import java.io.File;

/**
 * Created by ao on 2017/10/23.
 */
public class ServiceClassGenerator  extends AbstractClassGenerator{
    private static final String CONFIG_FILE = "IService";

    @Override
    protected String getConfFile() {
        return CONFIG_FILE;
    }

    @Override
    protected String getFileName(Table table) {
        return "service"+ File.separator+ NameUtils.getClassName(table.getTableName())+"Service.java";
    }

    static class ServiceFactory implements Factory<ServiceClassGenerator> {

        @Override
        public ServiceClassGenerator create() {
            return new ServiceClassGenerator();
        }
    }
}
