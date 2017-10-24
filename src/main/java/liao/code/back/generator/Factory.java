package liao.code.back.generator;

/**
 * Created by ao on 2017/10/24.
 */
public interface Factory<T extends AbstractClassGenerator> {
    public T create();
}
