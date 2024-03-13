package cn.toutatis.xvoid.achievable.filter;

import java.util.List;

/**
 * 词汇过滤器
 * 查找单句中包含的词汇
 * 可使用字典树实现
 * @see cn.toutatis.xvoid.structure.tree.TrieNode 字典树
 */
public interface WordSearchFilter {

    /**
     * 添加词汇到过滤器
     * @param word 词汇
     */
    void addSearchWord(String word);

    /**
     * 查找文本
     * @param text 文本
     * @return 过滤结果
     */
    List<WordSearchResult> search(String text);

}
