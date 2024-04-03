package cn.toutatis.xvoid.structure.tree;

/**
 * 字典树
 * @param <T>
 */
public class TrieNode<T> extends AbstractNode<TrieNode<T>,T>{

    /**
     * 是否为结束节点
     */
    private boolean isEndNode = false;

    public boolean isEndNode() {
        return isEndNode;
    }

    public void setEndNode(boolean endNode) {
        isEndNode = endNode;
    }
}