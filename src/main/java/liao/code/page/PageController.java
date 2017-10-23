package liao.code.page;

import liao.code.page.model.Element;
import liao.code.page.model.PageTable;
import liao.code.page.util.ColNameSearchUtils;
import liao.code.page.util.ParseHtml;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseMySQLDDL;
import liao.parse.table.mysql.ParseTableForMySQL;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ao on 2017/10/18.
 */
@RequestMapping("page")
@Controller
public class PageController {
    @RequestMapping("init")
    public ModelAndView init(String tableName,String path){
        Table table = new ParseTableForMySQL(tableName).getTable();
        PageTable pageTable = ParseHtml.getAllElement(path);
        ModelAndView mv = new ModelAndView("pageInfo");
        mv.addObject("eleInfo",pageTable.getElementList());
        return mv;
    }
    public void createPageElement(Element ele, Table table){
        Column col = ColNameSearchUtils.getMatchCol(table,ele.getEleName());
        if(col != null){
            ele.setDbTable(table.getTableName());
            ele.setBeanName(table.getAlias());
            ele.setIsNullable(col.isNullable());
            ele.setType(col.getColJavaType());
            ele.setColName(col.getCamelColName());
        }
    }
}
