package liao.code.generator.page.enums;

import liao.utils.EnumValue;
import liao.utils.PropertyUtils;

import java.util.Properties;

/**
 * Created by ao on 2017/10/25.
 */
public enum InputTypeEnum implements EnumValue{
    INPUT_TEXT(1,"inputText","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("input_text_model");
        }
    },
    RADIO(2,"radio","getRadioValue") {
        @Override
        public String htmlModel() {
            return pro.getProperty("radio_model");
        }
    },
    CHECKBOX(3,"checkbox","getCheckBoxValues") {
        @Override
        public String htmlModel() {
            return pro.getProperty("checkbox_model");
        }
    },
    TEXT(4,"TEXT","") {
        @Override
        public String htmlModel() {
            return pro.getProperty("text_model");
        }
    },
    SELECT(5,"select","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("select_model");
        }
    },
    DATE(6,"date","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("date_html_model");
        }
    },
    DATE_TIME(7,"datetime","getElementById") {
        @Override
        public String htmlModel() {
            return pro.getProperty("datetime_html_model");
        }
    };
    private int value;
    private String desc;
    private String getValueMethod;
    private static Properties pro = PropertyUtils.getConfig("htmlModel");
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
    public abstract String htmlModel();
}
