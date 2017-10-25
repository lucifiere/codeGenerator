package liao.code.page.generator;

import liao.code.back.generator.AbstractCodeGenerator;
import liao.code.page.enums.InputTypeEnum;
import liao.code.page.enums.LengthLimitEnum;
import liao.code.page.enums.NullableEnum;
import liao.code.page.enums.ValueTypeEnum;
import liao.code.page.model.Element;
import liao.code.page.model.PageTable;
import liao.parse.table.model.Table;
import liao.utils.CommonUtils;
import liao.utils.EnumCheck;

import java.util.List;

/**
 * Created by ao on 2017/10/24.
 */
public class JsGenerator extends AbstractCodeGenerator{
    protected String replaceModelCode(Table table, String model){

    }
    private StringBuilder getFormData(List<PageTable> page,String getEleModel){
        StringBuilder formData = new StringBuilder();
        formData.append("var #alias# = new Object();");
        for(PageTable table : page){
            for(Element element : table.getElementList()){
                InputTypeEnum typeEnum = CommonUtils.getEnumByValue(element.getInputType(),InputTypeEnum.values());
                formData.append("#alias#."+element.getColName()
                        +"="+typeEnum.getGetValueMethod()+"(\""+element.getColName()+"\")");
                formData.append(System.lineSeparator());
            }
        }
        return formData;
    }
    private String checkFormData(List<PageTable> page){
        StringBuilder checkData = new StringBuilder();
        checkData.append("var formDat = getFormData();");
        checkData.append(System.lineSeparator());
        for(PageTable table : page){
            for(Element element : table.getElementList()){
                checkData.append(checkNull(element));
                checkData.append(checkLength(element));
                checkData.append(checkValueType(element));
            }
        }
        checkData.append("return true;");
        return checkData.toString();
    }
    private String checkNull(Element ele){
        NullableEnum typeEnum = CommonUtils.getEnumByValue(ele.getIsNullable(), NullableEnum.values());
        if(typeEnum == NullableEnum.NO){
            return "";
        }else {
            return buildCheck(typeEnum,ele.getColName(),ele.getEleName());
        }
    }

    private String checkValueType(Element ele){
        ValueTypeEnum typeEnum = CommonUtils.getEnumByValue(ele.getTypeLimit(), ValueTypeEnum.values());
        if(typeEnum == ValueTypeEnum.STRING){
            return "";
        }else {
            return buildCheck(typeEnum,ele.getColName(),ele.getEleName());
        }
    }
    private String checkLength(Element ele){
        if(ele.getLengthLimit() == null){
            return "";
        }else {
            return buildCheck(LengthLimitEnum.LENGTH,ele.getColName()+","+ele.getLengthLimit(),ele.getEleName());
        }
    }
    private String buildCheck(EnumCheck value,String colName,String eleName){
        StringBuilder checkNull = new StringBuilder();
        checkNull.append("  if(!"+value.getCheckMethod()+"(formData."+colName+","+eleName+")){");
        checkNull.append(System.lineSeparator());
        checkNull.append("      return false;");
        checkNull.append(System.lineSeparator());
        checkNull.append("  }");
        checkNull.append(System.lineSeparator());
        return checkNull.toString();
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
