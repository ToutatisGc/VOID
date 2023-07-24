package cn.toutatis.xvoid.orm.mapping.concepts;

/**
 * @author Toutatis_Gc
 * 可命名的
 */
public interface Nameable {

    /**
     * @param name 名称
     */
    void setName(String name);

    /**
     * @return 命名内容
     */
    String getName();

}
