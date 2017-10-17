package liao.code.page.util;

import liao.utils.ReaderModelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ao on 2017/10/17.
 */
public class ParseHtml {
    private static final String HANZI = "[\\u4e00-\\u9fa5]";
    private static final String NOT_PLAIN_TEXT = "= *[\"|'].+"+HANZI+".+[\"|']";
    private static final String GET_TEXT = ">.*"+HANZI+"+.*<";
    private static final String HANZI_BEFORE = ".*> *";
    private static final String HANZI_AFTER = " *< *";
    private static final String HTML_HEAD = ".*< +body *>";
    private static final String HTML_TAIL ="< */ +body *>.*";
    public List<String> getEle(String path){
        try {
            List<String> html = ReaderModelUtils.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
