package liao.code.generator.page.model;

import java.util.List;

/**
 * Created by ao on 2017/10/17.
 */
public class PageTable {
    private String tableName;
    private String eleExample;
    private List<Element> elementList;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getEleExample() {
        return eleExample;
    }

    public void setEleExample(String eleExample) {
        this.eleExample = eleExample;
    }

    public List<Element> getElementList() {
        return elementList;
    }

    public void setElementList(List<Element> elementList) {
        this.elementList = elementList;
    }
}
