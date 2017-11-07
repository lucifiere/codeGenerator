package liao.code.generator.controller;

import liao.code.generator.page.enums.InputTypeEnum;
import liao.code.generator.page.enums.ValueTypeEnum;
import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.Page;
import liao.code.generator.page.util.ColNameSearchUtils;
import liao.code.generator.page.util.ParseHtml;
import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseTableForMySQL;
import liao.utils.TwoTuple;
import liao.utils.enums.WhetherEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ao on 2017/10/27.
 */
@Controller
@RequestMapping("conf")
public class ConfController {
    private static final ConcurrentHashMap<String,List<Table>> tableCache = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String,Page> pageCache = new ConcurrentHashMap<>();
    @RequestMapping("start")
    public ModelAndView start(){
        ModelAndView mv = new ModelAndView("start");
        return mv;
    }
    @RequestMapping("getPageConf")
    @ResponseBody
    public ModelAndView getPageConf( String tableNames,String htmlPath, Integer useCache){
        List<Table> tableList = null;
        Page page = null;
        if(useCache == WhetherEnum.YES.getValue()){
            tableList = tableCache.get(tableNames);
            page = pageCache.get(htmlPath);
        }
        if(tableList == null){
            tableList = getTableList(tableNames);
            tableCache.put(tableNames,tableList);
        }
        if(page == null){
            page = createPageInfo(htmlPath,tableList);
            pageCache.put(htmlPath,page);
        }
        ModelAndView mv = new ModelAndView("pageInfo");
        mv.addObject("pageInfo",page);
        mv.addObject("inputTypeList", InputTypeEnum.values());
        mv.addObject("valueTypeList", ValueTypeEnum.values());
        mv.addObject("whetherList", WhetherEnum.values());
        return mv;
    }
    private List<Table> getTableList(String tableNames){
        String[] tableNameList = tableNames.split(",");
        List<Table> tableList = new ArrayList<>(tableNameList.length);
        for(String tableName : tableNameList){
            ParseTableForMySQL parseTable = new ParseTableForMySQL(tableName);
            tableList.add(parseTable.getTable());
        }
        return tableList;
    }
    @RequestMapping("allElementList")
    @ResponseBody
    public List<Column> allElementList(String tableNames,String tableName,String colName){
        List<Table> tableList = tableCache.get(tableNames);
        if(tableList == null){
            tableList = getTableList(tableNames);
            tableCache.put(tableNames,tableList);
        }
        List<Column> allColumn = new ArrayList<>();
        for(Table table : tableList){
            if(tableName == null || table.getTableName().equals(tableName)) {
                for(Column col : table.getColumnList()) {
                    if (colName == null || colName.trim().length() == 0) {
                        if(col.getColName().contains(colName)){
                            allColumn.add(col);
                        }
                    }
                }
            }
        }
        return allColumn;
    }

    private Page createPageInfo(String path,List<Table> tableList){
        Page page = ParseHtml.getAllElement(path);
        List<Element> elementList = page.getPageTableList().get(0).getElementList();
        for(Element element : elementList){
            TwoTuple<Table,Column> twoTuple  = new ColNameSearchUtils().getMatchCol(tableList,element.getEleName());
            if(twoTuple != null){
                Column col = twoTuple.getValue();
                Table table = twoTuple.getKey();
                element.setColName(col.getCamelColName());
                element.setDbColName(col.getColName());
                element.setDbComment(col.getComment());
                element.setBeanName(table.getAlias());
                element.setDbTable(table.getTableName());
                element.setIsNullable(col.isNullable());
                element.setTypeLimit(getValueTypeEnum(col.getColJavaType()));
            }
        }
        return page;
    }

    private Integer getValueTypeEnum(String javaType){
        if("Long".equals(javaType) || "Integer".equals(javaType)){
            return ValueTypeEnum.INTEGER.getValue();
        }else if("BigDecimal".equals(javaType) ){
            return ValueTypeEnum.TWO_SCALE.getValue();
        }else {
            return ValueTypeEnum.STRING.getValue();
        }
    }
    @RequestMapping("changeDBColName")
    @ResponseBody
    public List<Element> changeDBColName(String dbColName,String tableNames){
        List<Table> tableList = tableCache.get(tableNames);
        if(tableList == null){
            tableList = getTableList(tableNames);
            tableCache.put(tableNames,tableList);
        }
        List<Element> resultList = new ArrayList<>();
        for(Table table : tableList){
            for(Column col : table.getColumnList()){
                if(col.getColName().equals(dbColName)){
                    resultList.add(createElementByCol(table,col));
                }
            }
        }
        return resultList;
    }
    private Element createElementByCol(Table table,Column col){
        Element element = new Element();
        element.setColName(col.getCamelColName());
        element.setDbColName(col.getColName());
        element.setDbComment(col.getComment());
        element.setBeanName(table.getAlias());
        element.setDbTable(table.getTableName());
        element.setIsNullable(col.isNullable());
        element.setTypeLimit(getValueTypeEnum(col.getColJavaType()));
        return element;
    }



}
