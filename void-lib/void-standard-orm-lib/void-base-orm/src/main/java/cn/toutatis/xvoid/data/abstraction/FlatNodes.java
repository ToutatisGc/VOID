package cn.toutatis.xvoid.data.abstraction;


import java.util.List;

/**
 * @author Toutatis_Gc
 * @date 2022/8/18 22:46
 * 扁平化节点
 */
public class FlatNodes<T> {

    private String name;

    private String description;

    private Object extra;

    private List<T> nodes;

}
