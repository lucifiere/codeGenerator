package liao;

import liao.code.back.generator.AbstractClassGenerator;
import liao.code.back.generator.BeanClassGenerator;
import liao.code.back.generator.RegistrationFactory;
import liao.code.back.generator.SqlGenerator;
import liao.parse.table.model.Table;
import liao.parse.table.mysql.ParseTableForMySQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ao on 2017/10/16.
 */
public class Start {
    public static void  main(String[] args){
        System.out.println("输入DDL");
        Scanner sc = new Scanner(System.in);
        String tableName = sc.nextLine().trim();
        Table table = new ParseTableForMySQL(tableName).getTable();
        List<AbstractClassGenerator> generatorList = RegistrationFactory.getGeneratorList();
        for(AbstractClassGenerator classGenerator : generatorList){
            classGenerator.generatorCode(table);
        }
        SqlGenerator.generatorSQL(table);
    }
}
