package cn.toutatis.xvoid.achievable.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 单词查找结果
 */
@Data
@AllArgsConstructor
public class WordSearchResult {

    /**
     * 单词的位置
     */
    private List<Integer> position;

    /**
     * 匹配词汇
     */
    private String word;

    @Override
    public String toString() {
        return String.format("单词[%s]-位置%s",word,position);
    }
}
