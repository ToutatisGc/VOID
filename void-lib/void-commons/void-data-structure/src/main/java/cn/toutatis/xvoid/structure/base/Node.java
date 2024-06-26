package cn.toutatis.xvoid.structure.base;

import java.util.Map;

/**
 * 节点
 * @param <T> 数据类型
 */
public interface Node<NT,T> {

    /**
     * 获取子节点
     * @return 子节点
     */
    Map<T, NT> childrenNodes();

}
