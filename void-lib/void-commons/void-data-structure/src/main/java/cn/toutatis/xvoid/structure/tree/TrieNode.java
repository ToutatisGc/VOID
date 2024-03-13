package cn.toutatis.xvoid.structure.tree;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典树
 * @param <T>
 */
public class TrieNode<T> {

    /**
     * 子节点
     */
    private Map<T, TrieNode<T>> childrenNodes = new HashMap<>();

    /**
     * 是否为结束节点
     */
    private boolean isEndNode = false;

    public Map<T, TrieNode<T>> childrenNodes() {
        return childrenNodes;
    }

    public void setChildrenNodes(Map<T, TrieNode<T>> childrenNodes) {
        this.childrenNodes = childrenNodes;
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public void setEndNode(boolean endNode) {
        isEndNode = endNode;
    }
}