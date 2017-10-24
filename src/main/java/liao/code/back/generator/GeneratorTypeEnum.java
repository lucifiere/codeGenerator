package liao.code.back.generator;

/**
 * Created by ao on 2017/10/24.
 */
public enum GeneratorTypeEnum {
    PO(1,new BeanClassGenerator.BeanFactory()),
    VO(2,new VoModelClassGenerator.VoFactory()),
    DAO(3,new DaoClassGenerator.DaoFactory()),
    DAO_IMPL(4,new DaoImplClassGenerator.DaoImplFactory()),
    SERVICE(5,new ServiceClassGenerator.ServiceFactory()),
    SERVICE_IMPL(6,new ServiceImplClassGenerator.ServiceImplFactory()),
    CONTROLLER(7,new ControllerClassGenerator.ControllerFactory());
    private int type;
    private Factory factory;

    GeneratorTypeEnum(int i, Factory factory) {
        this.type = type;
        this.factory = factory;
    }

    public Factory getFactory() {
        return factory;
    }
}
