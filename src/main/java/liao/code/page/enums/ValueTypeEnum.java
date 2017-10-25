package liao.code.page.enums;

import liao.utils.EnumCheck;

/**
 * Created by ao on 2017/10/25.
 */
public enum ValueTypeEnum implements EnumCheck {
    INTEGER(1,"正整数","checkIsNumber","blurCheckIsNumber"),
    TWO_SCALE(2,"两位小数","checkIsTowScaleNumber","blurCheckIsTwoScaleNumber"),
    STRING(3,"字符串","checkIsTowScaleNumber","blurCheckIsTwoScaleNumber");
    private int value;
    private String desc;
    private String checkMethod;
    private String inputCheckMethod;
    private String blurCheckMethod;



    ValueTypeEnum(int value, String desc, String checkMethod,String blurCheckMethod) {
        this.value = value;
        this.desc = desc;
        this.checkMethod = checkMethod;
        this.blurCheckMethod = blurCheckMethod;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public String getBlurCheckMethod() {
        return blurCheckMethod;
    }
}
