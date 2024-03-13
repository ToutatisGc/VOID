package cn.toutatis.xvoid.toolkit.validator;

import java.util.Set;

/**
 * 词汇过滤器
 * 查找单句中包含的词汇
 */
public interface WordFilter {

    /**
     * 添加词汇
     * @param word 敏感词
     */
    void addWord(String word);

    Set<String> filter(String text);

    String replaceWord(String text,String word);

}
