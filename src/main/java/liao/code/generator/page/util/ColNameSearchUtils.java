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
    private String ignoreChar;
    private static final int minLength = 3;
    public TwoTuple<Table,Column> getMatchCol(List<Table> tableList,String eleName){
        TreeSet<Result> tree = new TreeSet<>();
        for(int i = 0;i < tableList.size(); i++){
            Table table = tableList.get(i);
            ifNotNullAddTree(getMatchCol(table,eleName,i),tree);
        }
        Result result = notEmptyGetFirst(tree);
        return result == null ? null : new TwoTuple<Table,Column>(result.table,result.column);
    }
    public Result getMatchCol(Table table,String eleName,int i){
        Result matchCol = pageMatchDB(table,eleName,i);
        return matchCol;
    }
    private Result pageMatchDB(Table table,String eleName,int tableIndex){

        TreeSet<Result> tree = new TreeSet<Result>();
        ifNotNullAddTree(orderMatch(table,eleName,tableIndex),tree);
        ifNotNullAddTree(reverseMatch(table, eleName, tableIndex),tree);
        ifNotNullAddTree(middleMatch(table, eleName, tableIndex),tree);
        return notEmptyGetFirst(tree);
    }
    private void ifNotNullAddTree(Result result,TreeSet<Result> tree){
        if(result != null) {
            tree.add(result);
        }
    }
    private Result notEmptyGetFirst(TreeSet<Result> tree){
        return tree.isEmpty() ? null : tree.first();
    }

    private Result orderMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength > eleName.length() ? eleName.length() : minLength;
        boolean nextTable = false;
        for(int i = eleName.length(); i >= minLen;i--){
            String matchStr = eleName.substring(0,i);
            for(Column col : columnList){
                String comment = col.getComment();
                boolean isMatch =  comment.contains(matchStr);
                if(!nextTable){
                    nextTable = isMatch;
                }
                if(isMatch) {
                    matchColSet.add(new Result(matchStr.length(), 1, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
                }
            }
            if(nextTable){
                break;
            }
        }
        return notEmptyGetFirst(matchColSet);
    }

    public Result middleMatch(Table table,String eleName,int tableIndex){
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength > eleName.length() ? eleName.length() : minLength;
        int startIndex = 1;
        int endIndex = 1;
        for(;;){
            String matchStr = eleName.substring(startIndex,eleName.length() - endIndex);
            if(matchStr.length() < minLen){
                break;
            }
            TreeSet<Result> matchCol = strMatchColName(matchStr,eleName,table,tableIndex,3);
            matchColSet.addAll(matchCol);
            if(!matchCol.isEmpty()){
                break;
            }
            //先从后往前缩减
            if(startIndex < endIndex){
                startIndex++;
            }else{
                endIndex++;
            }
        }
        return notEmptyGetFirst(matchColSet);
    }
    private TreeSet<Result> strMatchColName(String matchStr,String eleName,Table table,int tableIndex,int type){
        TreeSet<Result> matchColSet = new TreeSet<>();
        for(Column col : table.getColumnList()){
            String comment = col.getComment();
            boolean isMatch =  comment.contains(matchStr);
            if(isMatch) {
                matchColSet.add(new Result(matchStr.length(), type, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
            }
        }
        return matchColSet;
    }
    private Result reverseMatch(Table table,String eleName,int tableIndex){
        List<Column> columnList = table.getColumnList();
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen =minLength > eleName.length() ? 1 : eleName.length() - minLength+1;
        boolean nextTable = false;
        for(int i = 1; i <= minLen;i++){
            String matchStr = eleName.substring(i-1,eleName.length());
            for(Column col : columnList){
                String comment = col.getComment();
                boolean isMatch =  comment.contains(matchStr);
                if(!nextTable){
                    nextTable = isMatch;
                }
                if(isMatch) {
                    matchColSet.add(new Result(matchStr.length(), 3, col, table, true, Math.abs(eleName.length() - comment.length()), tableIndex));
                }
            }
            if(nextTable){
                break;
            }
        }
        return notEmptyGetFirst(matchColSet);
    }
    private Column dbMatchPage(List<Column> columnList,String eleName){
        return null;
    }

    /**
     * 发现名称前缀部分，提高匹配精度
     * @return
     */
    private String findNamePre(){
        return  "";
    }

    private static class Result implements Comparable<Result>{
        int matchLength;//字符相匹配的长度
        int matchOrder;//匹配顺序
        Column column;
        private Table table;
        boolean isPageMatchDB;//是否页面匹配字段名匹配数据库成功
        int lengthDiff;//相匹配的字符串字符数差
        int tableIndex;//第几张表匹配
        public Result(int matchLength,
                int matchOrder,
                Column column, Table table,
                boolean isPageMatchDB,
                int lengthDiff,
                int tableIndex){
            this.column = column;
            this.table = table;
            this.matchLength = matchLength;
            this.matchOrder = matchOrder; //正序匹配
            this.isPageMatchDB = isPageMatchDB;
            this.lengthDiff = lengthDiff;
            this.tableIndex = tableIndex;
        }

        @Override
        public int compareTo(Result o) {
            if(o.matchLength > this.matchLength){//字符相匹配的长度
                return 1;
            }else if(o.matchLength == this.matchLength){
                if(o.lengthDiff > this.lengthDiff ){//相匹配的字符串字符数差，差距越小匹配度越高
                    return -1;
                }else if(lengthDiff < this.lengthDiff){
                    return 1;
                }else {
                    if(this.tableIndex == o.tableIndex) {//第几张表匹配
                        if (this.matchOrder == o.matchOrder) {//正序匹配
                            if (this.isPageMatchDB) {//是否页面匹配字段名匹配数据库成功
                                return 1;
                            } else {
                                return -1;
                            }
                        } else if (this.matchOrder < o.matchOrder) {
                            return -1;
                        } else {
                            return 1;
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
