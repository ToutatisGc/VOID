package cn.toutatis.xvoid.achievable.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 词汇查找结果
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
        return String.format("单词[%s]-位置%s",word,position == null ? "未匹配" : position.toString());
    }

    /**
     * 获取单词出现频率
     */
    public int getFrequency(){
        if (position == null) return 0;
        return position.size();
    }
}
