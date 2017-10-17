package liao.code.page.model;

import java.util.List;

/**
 * Created by ao on 2017/10/17.
 */
public class Element {
    private String eleName; //中文名称
    private String colName; //对应的字段
    private String dbTable; //所在表
    private String beanName; //锁在bean名称
    private String type;  //输入类型
    private boolean isNullable; //必填
    private Integer lengthLimit; //长度限制
    private String typeLimit; //输入值限制
    private List<String> valueList;  //可选值

    public String getEleName() {
        return eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public Integer getLengthLimit() {
        return lengthLimit;
    }

    public void setLengthLimit(Integer lengthLimit) {
        this.lengthLimit = lengthLimit;
    }

    public String getTypeLimit() {
        return typeLimit;
    }

    public void setTypeLimit(String typeLimit) {
        this.typeLimit = typeLimit;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }
}
