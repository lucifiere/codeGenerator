package liao.code.page.util;

import liao.parse.table.model.Column;
import liao.parse.table.model.Table;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by ao on 2017/10/17.
 */
public class ColNameSearchUtils {
    private static final int minLength = 3;
    public static Column getMatchCol(Table table,String eleName){
        Result matchCol = pageMatchDB(table.getColumnList(),eleName);
        return matchCol.column;
    }
    private static Result pageMatchDB(List<Column> columnList,String eleName){
        TreeSet<Result> tree = new TreeSet<Result>();
        tree.add(orderMatch(columnList,eleName));
        tree.add(reverseMatch(columnList,eleName));
        return tree.last();
    }

    private static Result orderMatch(List<Column> columnList,String eleName){
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength < eleName.length() ? eleName.length() : minLength;
        for(int i = eleName.length(); i <= minLen;i--){
            String matchStr = eleName.substring(1,i-1);
            for(Column col : columnList){
                String comment = col.getComment().split(" |:|：")[0];
                comment.contains(matchStr);
                matchColSet.add(new Result(i,true,col,true,Math.abs(eleName.length()-comment.length())));
            }
        }
        return matchColSet.last();
    }
    private static Result reverseMatch(List<Column> columnList,String eleName){
        TreeSet<Result> matchColSet = new TreeSet<>();
        int minLen = minLength < eleName.length() ? eleName.length() : minLength;
        for(int i = minLen; i <= eleName.length();i++){
            String matchStr = eleName.substring(eleName.length()-i,eleName.length());
            for(Column col : columnList){
                String comment = col.getComment().split(" |:|：")[0];
                comment.contains(matchStr);
                matchColSet.add(new Result(i,false,col,true,Math.abs(eleName.length()-comment.length())));
            }
        }
        return matchColSet.last();
    }
    private static Column dbMatchPage(List<Column> columnList,String eleName){
        return null;
    }

    private static class Result implements Comparable<Result>{
        int matchLength;
        boolean isOrderMatch;
        Column column;
        boolean isPageMatchDB;
        int lengthDiff;
        public Result(int matchLength,
                boolean isOrderMatch,
                Column column,
                boolean isPageMatchDB,
                int lengthDiff){
            this.column = column;
            this.matchLength = matchLength;
            this.isOrderMatch = isOrderMatch;
            this.isPageMatchDB = isPageMatchDB;
            this.lengthDiff = lengthDiff;
        }

        @Override
        public int compareTo(Result o) {
            if(o.matchLength > this.matchLength){
                return 1;
            }else if(o.matchLength == this.matchLength){
                if(this.lengthDiff < o.lengthDiff){
                    return 1;
                }else if(this.lengthDiff > o.lengthDiff){
                    return -1;
                }else {
                    if (this.isOrderMatch == o.isOrderMatch) {
                        if (this.isPageMatchDB) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else if (this.isOrderMatch) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }else{
                return -1;
            }
        }
    }
}
