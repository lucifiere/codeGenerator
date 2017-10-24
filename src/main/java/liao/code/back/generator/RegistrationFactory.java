package liao.code.back.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ao on 2017/10/24.
 */
public class RegistrationFactory {
    public static List<AbstractClassGenerator> getGeneratorList(){
        List<AbstractClassGenerator> resultList = new ArrayList<>();
        for(GeneratorTypeEnum type : GeneratorTypeEnum.values()){
            resultList.add(type.getFactory().create());
        }
        return resultList;
    }
}
