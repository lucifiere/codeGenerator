package liao.code.generator.page;

import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.PageTable;
import liao.code.generator.page.util.ColNameSearchUtils;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

/**
 * Created by ao on 2017/10/17.
 */
public class CreatePageInfo {
    public static void createPage(PageTable page, Table table){
        for(Element ele : page.getElementList()){
            Column col = ColNameSearchUtils.getMatchCol(table,ele.getEleName());
            if(col != null){
                ele.setColName(col.getCamelColName());
                ele.setBeanName(table.getAlias());
                ele.setDbTable(table.getTableName());
                ele.setIsNullable(col.isNullable());
                ele.setTypeLimit(1);
            }
        }
    }
}
