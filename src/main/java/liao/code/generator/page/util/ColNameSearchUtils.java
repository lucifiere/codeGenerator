package liao.code.generator.page.util;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;
import liao.utils.TwoTuple;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by ao on 2017/10/17.
 */
public class ColNameSearchUtils {
    private static final int minLength = 3;
    public static TwoTuple<Table,Column> getMatchCol(List<Table> tableList,String eleName){
        TreeSet<Result> tree = new TreeSet<>();
        for(int i = 0;i < tableList.size(); i++){
            Table table = tableList.get(i);
            tree.add(getMatchCol(table,eleName,i));
        }
        Result result = tree.last();
        return new TwoTuple<Table,Column>(result.table,result.column);
    }
    public static Result getMatchCol(Table table,String eleName,int i){
        Result matchCol = pageMatchDB(table,eleName,i);
        return matchCol;
    }
    private static Result pageMatchDB(Table table,String eleName,int tableIndex){

        TreeSet<Result> tree = new TreeSet<Result>();
        tree.add(orderMatch(table,eleName,tableIndex));
        tree.add(reverseMatch(table,eleName,tableIndex));
        return tree.last();
    }

    private static Result orderMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength < eleName.length() ? eleName.length() : minLength;
        for(int i = eleName.length(); i <= minLen;i--){
            String matchStr = eleName.substring(1,i-1);
            for(Column col : columnList){
                String comment = col.getComment().split(" |:|：")[0];
                comment.contains(matchStr);
                matchColSet.add(new Result(i,true,col,table,true,Math.abs(eleName.length()-comment.length()),tableIndex));
            }
        }
        return matchColSet.last();
    }
    private static Result reverseMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength < eleName.length() ? eleName.length() : minLength;
        for(int i = minLen; i <= eleName.length();i++){
            String matchStr = eleName.substring(eleName.length()-i,eleName.length());
            for(Column col : columnList){
                String comment = col.getComment().split(" |:|：")[0];
                comment.contains(matchStr);
                matchColSet.add(new Result(i,false,col,table,true,Math.abs(eleName.length()-comment.length()),tableIndex));
            }
        }
        return matchColSet.last();
    }
    private static Column dbMatchPage(List<Column> columnList,String eleName){
        return null;
    }

    private static class Result implements Comparable<Result>{
        int matchLength;//字符相匹配的长度
        boolean isOrderMatch;//正序匹配
        Column column;
        private Table table;
        boolean isPageMatchDB;//是否页面匹配字段名匹配数据库成功
        int lengthDiff;//相匹配的字符串字符数差
        int tableIndex;//第几张表匹配
        public Result(int matchLength,
                boolean isOrderMatch,
                Column column, Table table,
                boolean isPageMatchDB,
                int lengthDiff,
                int tableIndex){
            this.column = column;
            this.table = table;
            this.matchLength = matchLength;
            this.isOrderMatch = isOrderMatch; //正序匹配
            this.isPageMatchDB = isPageMatchDB;
            this.lengthDiff = lengthDiff;
            this.tableIndex = tableIndex;
        }

        @Override
        public int compareTo(Result o) {
            if(o.matchLength > this.matchLength){//字符相匹配的长度
                return 1;
            }else if(o.matchLength == this.matchLength){
                if(this.lengthDiff < o.lengthDiff){//相匹配的字符串字符数差，差距越小匹配度越高
                    return 1;
                }else if(this.lengthDiff > o.lengthDiff){
                    return -1;
                }else {
                    if(this.tableIndex == o.tableIndex) {//第几张表匹配
                        if (this.isOrderMatch == o.isOrderMatch) {//正序匹配
                            if (this.isPageMatchDB) {//是否页面匹配字段名匹配数据库成功
                                return 1;
                            } else {
                                return -1;
                            }
                        } else if (this.isOrderMatch) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }else if(this.tableIndex < o.tableIndex){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            }else{
                return -1;
            }
        }
    }
}
