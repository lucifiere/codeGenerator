package liao.code.page.util;

import liao.utils.ReaderUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ao on 2017/10/17.
 */
public class ParseHtml {
    private static final String HANZI = "[\\u4e00-\\u9fa5]";
    private static final String NOT_PLAIN_TEXT = "=\\s*[\"|'][^>]*"+HANZI+"[^>]*[\"|']";
    private static final String GET_TEXT = "(<[^<]*>)?([^>]*"+HANZI+"+[^<]*)";
    private static final String HANZI_BEFORE = "..*>";
    private static final String HANZI_AFTER = "：|：";
    private static final String COMMENT = "<!--.+\\n*\\r*|-->";
    private static final String HTML_HEAD = ".+<\\s*body\\s*>";
    private static final String HTML_TAIL ="<\\s*/\\s*body\\s*>.*";
    private static final String IGNORE_TYPE = "(<\\s*option.+>)|(<\\s*input.+>)";
    private static final String SELECT_TYPE = "("+HANZI+"+)";
    private static final String SPECIAL_CHAR = "只|个|元|分|角|查看";
    private static final Pattern GET_HANZI_PATTERN = Pattern.compile(GET_TEXT);
    private static final Pattern HANZI_PATTERN = Pattern.compile(HANZI);
    private static final Pattern HTML_HEAD_PATTERN = Pattern.compile(HTML_HEAD);
    private static final Pattern HTML_TAIL_PATTERN = Pattern.compile(HTML_TAIL);
    private static final Pattern HANZI_BEFORE_PATTERN = Pattern.compile(HANZI_BEFORE);
    private static final Pattern HANZI_AFTER_PATTERN = Pattern.compile(HANZI_AFTER);


    public static List<String> getAllElement(String path){
        try {
            List<String> htmlLines = ReaderUtils.readAllLines(path);
            List<String> eleList = new ArrayList<>();
            for(String line : htmlLines){
                eleList.addAll(getElementNameList(line));
            }
            return eleList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static List<String> getElementNameList(String line){
        List<String> eleList = new ArrayList<>();
        line = line.replaceAll(NOT_PLAIN_TEXT,""); //去掉非纯文本的汉字
        line = line.replaceAll(COMMENT,""); //去掉注释
        HANZI_PATTERN.matcher(line);
        Matcher matcher =GET_HANZI_PATTERN.matcher(line);
        while (matcher.find()){
            System.out.println(line);
            String type = matcher.group(1);
            String ele = matcher.group(2);
            ele = ele.replaceAll(HANZI_BEFORE,"");
            ele = ele.replaceAll(HANZI_AFTER,"");
            ele = ele.trim();
            if(!type.toLowerCase().matches(IGNORE_TYPE) && !SPECIAL_CHAR.contains(ele)) {
                eleList.add(ele);
            }
        }
        return eleList;
    }
    private static List<String> formatHtml(List<String> htmlLines){
        return htmlLines;
    }
    private static List<String> removeHtmlHeadAndTail(List<String> htmlLines){
       for(String lines : htmlLines){

       }
        return htmlLines;
    }
    private static String getTitle(List<String> htmlLines){
        return "";
    }

    private static boolean htmlTypeNeedIgnore(String line){
        return false;
    }
    private static void getTableTag(List<String> htmlLines){

    }
    public static void main(String[] args){
        System.out.println(getAllElement("C:\\Users\\ao\\Desktop\\财务-渤海银行扣款查询原型\\渤海扣款详情.html"));
    }

}
