package cn.toutatis.xvoid.toolkit.validator;

import java.util.HashMap;
import java.util.Map;

public class DFAFilter {
    @SuppressWarnings("rawtypes")
    private final Map<Character, Map> sensitiveWordsMap = new HashMap<>();
    private static final char EOS = '\0';

    public void addSensitiveWord(String word) {
        Map<Character, Map> currentMap = sensitiveWordsMap;
        for (char c : word.toCharArray()) {
            if (!currentMap.containsKey(c)) {
                currentMap.put(c, new HashMap<>());
            }
            currentMap = currentMap.get(c);
        }
        currentMap.put(EOS, null);
    }

    public void buildFilter(String[] sensitiveWords) {
        for (String word : sensitiveWords) {
            addSensitiveWord(word);
        }
    }

    public String filter(String text) {
        StringBuilder result = new StringBuilder();
        int start = 0;
        while (start < text.length()) {
            int end = start;
            Map<Character, Map> currentMap = sensitiveWordsMap;
            while (end < text.length() && currentMap.containsKey(text.charAt(end))) {
                currentMap = currentMap.get(text.charAt(end));
                if (currentMap.containsKey(EOS)) {
                    for (int i = start; i <= end; i++) {
                        result.append('*');
                    }
                    start = end + 1;
                    break;
                }
                end++;
            }
            if (end == start) {
                result.append(text.charAt(start));
                start++;
            }
        }
        return result.toString();
    }

    // Example Usage
    public static void main(String[] args) {
        String[] sensitiveWords = {"敏感词1", "敏感词2", "敏感词*"};
        DFAFilter dfaFilter = new DFAFilter();
        dfaFilter.buildFilter(sensitiveWords);
        String text = "这是一个测试，包含敏感词1，敏感词2，敏感词*以及其他词语。";
        String filteredText = dfaFilter.filter(text);
        System.out.println(filteredText);
    }
}