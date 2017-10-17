package liao.code.page;

import liao.code.page.model.Element;
import liao.code.page.model.PageTable;
import liao.code.page.util.ColNameSearchUtils;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.util.List;

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
                ele.setTypeLimit(col.getColJavaType());
            }
        }
    }
}
