package cn.toutatis.xvoid.structure.tree;

import cn.toutatis.xvoid.structure.base.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象节点
 * @param <NT> 节点类型
 * @param <T> 节点键值类型
 */
public class AbstractNode<NT extends AbstractNode<NT,T>,T> implements Node<NT,T> {

    /**
     * 子节点
     */
    protected Map<T, NT> childrenNodes = new HashMap<>();

    @Override
    public Map<T, NT> childrenNodes() {
        return childrenNodes;
    }

    /**
     * 获取树最大深度
     * @return 最大深度
     */
    public int maxDepth() {
        return maxDepth(this);
    }

    /**
     * 获取树最大深度
     * @param root 根节点
     * @return 最大深度
     */
    public int maxDepth(AbstractNode<NT,T> root) {
        if (root == null || root.childrenNodes().isEmpty()) {
            return 0;
        } else {
            int maxChildDepth = 0;
            for (Map.Entry<T, NT> nodeEntry : root.childrenNodes().entrySet()) {
                int childDepth = maxDepth(nodeEntry.getValue());
                maxChildDepth = Math.max(maxChildDepth, childDepth);
            }
            return maxChildDepth + 1;
        }
    }

    /**
     * 计算节点数量
     * @return 节点数量
     */
    public int countNodes() {
        return countNodes(this);
    }

    /**
     * 计算节点数量
     * @param root 根节点
     * @return 节点数量
     */
    public int countNodes(AbstractNode<NT,T> root) {
        if (root == null) {
            return 0;
        } else {
            int count = 1;
            for (Map.Entry<T, NT> nodeEntry : root.childrenNodes().entrySet()) {
                count += countNodes(nodeEntry.getValue()); // 递归计算子树的节点数量
            }
            return count;
        }
    }

}
