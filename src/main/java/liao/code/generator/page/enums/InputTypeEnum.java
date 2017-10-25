package liao.code.generator.page.enums;

import liao.utils.EnumValue;

/**
 * Created by ao on 2017/10/25.
 */
public enum InputTypeEnum implements EnumValue{
    TEXT(1,"text","getElementById"),
    RADIO(2,"radio","getRadioValue"),
    CHECKBOX(3,"checkbox","getCheckBoxValues");
    private int value;
    private String desc;
    private String getValueMethod;
    InputTypeEnum(int value, String desc,String getValueMethod) {
        this.value = value;
        this.desc = desc;
        this.getValueMethod = getValueMethod;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getGetValueMethod() {
        return getValueMethod;
    }
}
