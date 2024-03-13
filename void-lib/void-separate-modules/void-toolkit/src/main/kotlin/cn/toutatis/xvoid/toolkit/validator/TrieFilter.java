package cn.toutatis.xvoid.toolkit.validator;

import cn.toutatis.xvoid.structure.tree.TrieNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;



public class TrieFilter {
    private final TrieNode<Character> root = new TrieNode<>();
    public void addWord(String word) {
        TrieNode<Character> current = root;
        for (char c : word.toCharArray()) {
            current.childrenNodes().putIfAbsent(c, new TrieNode<>());
            current = current.childrenNodes().get(c);
        }
        current.setEndNode(true);
    }

    public Set<String> filter(String text) {
        Set<String> filteredWords = new HashSet<>();
        StringBuilder currentWord = new StringBuilder();
        TrieNode<Character> current = root;

        for (char c : text.toCharArray()) {
            if (current.childrenNodes().containsKey(c)) {
                current = current.childrenNodes().get(c);
                currentWord.append(c);
                if (current.isEndNode()) {
                    filteredWords.add(currentWord.toString());
                }
            } else {
                currentWord = new StringBuilder();
                current = root;
            }
        }
        return filteredWords;
    }

    public static void main(String[] args) throws IOException {
        TrieFilter filter = new TrieFilter();
        File file = new File("C:\\Users\\TOUTA\\Desktop\\wfc.dic");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.lines().forEach(filter::addWord);
            String text = "\"大漠漫漫长河，代理没有江南风荷的淡然；只希望遇一人牧马塞外，看大雪纷飞后红梅缀雪的静染！\"\n" +
                    "\t\t+\"那时，烧一只陶埙，与那大漠、胡杨共同吹起风沙雪落就已经是最安然的生活……有事感叹：人生像秋风扫落叶般，有时是那么的无情，有时反而给人一种缠绵的感觉！ \"\n" +
                    "\t    +\"看到秋菊凌寒而来，夕阳吐霞而归，那些，那些所谓的伤悲是不是可以放下？塞外牧马，食毡饮雪，虽然所有的一切看似不堪，可对于我来说，或许，那就是最好的去处！\"\n" +
                    "\t\t+\"不入红尘土，何染尘世泥？大漠孤烟直，长河落日圆，这又是一种怎样的心情呢？\"\n";
            Set<String> sensitiveWords = filter.filter(text);
            System.out.println("Sensitive Words: " + sensitiveWords);
        }
    }
}
