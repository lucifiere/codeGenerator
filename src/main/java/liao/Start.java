package liao;

import liao.code.generator.BeanClassGenerator;
import liao.code.generator.SqlGenerator;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseTableForMySQL;
import liao.utils.ReaderModelUtils;

import java.util.Scanner;

/**
 * Created by ao on 2017/10/16.
 */
public class Start {
    public static void  main(String[] args){
        System.out.println("输入DDL");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine().trim();
        sc.close();
        Table table = new ParseTableForMySQL(tableName).getTable();
        BeanClassGenerator.generatorBean(table);
        SqlGenerator.generatorSQL(table);
    }
}
