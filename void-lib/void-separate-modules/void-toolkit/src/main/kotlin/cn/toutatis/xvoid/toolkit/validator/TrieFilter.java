package cn.toutatis.xvoid.toolkit.validator;

import cn.toutatis.xvoid.achievable.filter.WordSearchResult;
import cn.toutatis.xvoid.structure.tree.TrieNode;
import cn.toutatis.xvoid.toolkit.constant.Regex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



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

    public List<String> filter(String text) {
        List<String> filteredWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        TrieNode<Character> current = root;
        for (int idx = 0; idx < text.toCharArray().length; idx++) {
            char currentCharacter = text.charAt(idx);
            boolean treeExist = current.childrenNodes().containsKey(currentCharacter);
            if (treeExist) {
                current = current.childrenNodes().get(currentCharacter);
                currentWord.append(currentCharacter);
                if (current.isEndNode()) {
                    filteredWords.add(currentWord.toString());
                }
            } else {
                if(currentWord.length() != 0){idx--;}
                currentWord = new StringBuilder();
                current = root;
            }
        }
        return filteredWords;
    }

    public static void main(String[] args) throws IOException {
        TrieFilter filter = new TrieFilter();
        File file = new File("C:\\Users\\TOUTA\\Desktop\\WORDS.txt");
        SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.lines().forEach(sensitiveWordFilter::addSearchWord);
//            String tx = "只av动漫下载";
            String text = "\"大漠漫漫长河，代 理没有江南风荷的淡然；只av动漫下载希望遇一人牧马塞外，看大雪纷飞后红梅缀雪的静染！\"\n" +
                    "\t\t+\"那时，烧一只陶埙，与那大漠、胡杨共同吹起风沙雪落就已经是最安然的生活……有事感叹：人生像秋风扫落叶般，有时是那么的无情，有时反而给人一种缠绵的感觉！ \"\n" +
                    "\t    +\"看到秋菊凌寒而来，夕阳吐霞而归，那些日本，那些所谓的伤悲是不是可以放下？塞外牧马，食毡饮雪，虽然所有的一切看似不堪，可对于我来说，或许，那就是最好的去处！\"\n" +
                    "\t\t+\"不入红尘土，何染尘世泥？大漠孤烟直，长河落日圆，专卖军用潜水刀这又是一种怎样的心情呢？\"\n";
            String t = "3月13日，中国外交部发言人汪文斌主持例行记者会。\n" +
                    "有记者提问，据媒体报道，美国总统拜登和前总统特朗普在昨天举行的最新一轮初选中都赢得了足够的党内代表票数，锁定了今年大选的两名总统候选人。鉴于中国关系是美国选民关注的主要话题之一，中方对美国两党总统提名有何评论？\n" +
                    "对此，汪文斌表示，美国大选是美国的内政，中方始终秉持不干涉别国内政原则，不干预美国大选。\n" +
                    "汪文斌还强调，发展好中美关系符合两国和两国人民的根本利益，也是国际社会的普遍期待。不管谁当选下一届美国总统，我们都希望美方能够同中方相向而行，按照相互尊重、和平共处、合作共赢的原则，推动中美关系朝着稳定、健康、可持续的方向发展，更好造福两国、惠及世界。";
            List<WordSearchResult> search = sensitiveWordFilter.search(Regex.convertSingleLine(t));
//            Set<String> sensitiveWords = filter.filter(text);
            System.out.println("Sensitive Words: " + search);
        }
    }
}
