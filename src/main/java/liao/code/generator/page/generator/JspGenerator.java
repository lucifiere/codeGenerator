package liao.code.generator.page.generator;

import liao.code.generator.AbstractCodeGenerator;
import liao.code.generator.page.enums.InputTypeEnum;
import liao.code.generator.page.enums.LengthLimitEnum;
import liao.code.generator.page.enums.NullableEnum;
import liao.code.generator.page.enums.ValueTypeEnum;
import liao.code.generator.page.model.Element;
import liao.code.generator.page.model.Page;
import liao.code.generator.page.model.PageTable;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.PropertyUtils;

import java.util.List;
import java.util.Properties;

/**
 * Created by ao on 2017/10/25.
 */
public class JspGenerator extends AbstractCodeGenerator{
    private static final Properties pro = PropertyUtils.getConfig("config");
    private static final String TABLE_MODEL = pro.getProperty("table_model");
    private static final String TD_MODEL=pro.getProperty("td_model");
    private static final String TR_MODEL =pro.getProperty("tr_model");
    private static final String TABLE_TAG_MODEL = pro.getProperty("table_title");

    protected String replaceModelCode(Table table, Page page, String model) {
        return null;
    }
    private String createListTable(PageTable table){

    }

    private String createListTableHead(List<Element> elementList){
        StringBuilder head = new StringBuilder();
        for(Element element : elementList){
            head.append(TD_MODEL.replace("#content#",element.getColName()));
        }
        return head.toString();
    }
    private String createTableBody(List<Element> elementList){
        StringBuilder head = new StringBuilder();
        for(Element element : elementList){

        }
        return head.toString();
    }
    private String createTdContent(Element element){
        StringBuilder content = new StringBuilder();
        InputTypeEnum inputType = CommonUtils.getEnumByValue(element.getInputType(),InputTypeEnum.values());
        ValueTypeEnum valueType = CommonUtils.getEnumByValue(element.getTypeLimit(),ValueTypeEnum.values());
        if(inputType == InputTypeEnum.INPUT_TEXT){
            if(!CommonUtils.isEmpty(valueType.getBlurCheckMethod())) {
                content.append("onblur=\""+valueType.getBlurCheckMethod()+"\"");
            }
            if(element.getLengthLimit() != null && element.getLengthLimit() > 0){
                content.append(LengthLimitEnum.LENGTH.getHtmlAttr()+"=\""+element.getLengthLimit()+"\"");
            }
            content.append(">");
        }else if(inputType == InputTypeEnum.RADIO){

        }else if(inputType == InputTypeEnum.RADIO){

        }else if(inputType == InputTypeEnum.TEXT){

        }
        return content.toString();
    }

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