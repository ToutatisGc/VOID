package cn.toutatis.xvoid.achievable.filter;

import cn.toutatis.xvoid.structure.tree.TrieNode;

/**
 * 默认支持词汇插入，需要实现search方法
 * 抽象词搜索过滤器
 * @author Toutatis_Gc
 */
public abstract class AbstractWordSearchFilter implements WordSearchFilter{

    /**
     * TrieNode根节点
     */
    protected TrieNode<Character> root = new TrieNode<>();

    @Override
    public void addSearchWord(String word) {
        TrieNode<Character> current = root;
        for (char c : word.toCharArray()) {
            current.childrenNodes().computeIfAbsent(c, k -> new TrieNode<>());
            current = current.childrenNodes().get(c);
        }
        current.setEndNode(true);
    }

    /**
     * 获取Trie树
     * @return TrieNode
     */
    public TrieNode<Character> trieTree() {
        return root;
    }
}
