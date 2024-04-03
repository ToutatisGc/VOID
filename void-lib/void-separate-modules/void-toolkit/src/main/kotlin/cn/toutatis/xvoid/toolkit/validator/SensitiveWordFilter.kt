package cn.toutatis.xvoid.toolkit.validator

import cn.toutatis.xvoid.achievable.filter.AbstractWordSearchFilter
import cn.toutatis.xvoid.achievable.filter.WordSearchResult
import cn.toutatis.xvoid.structure.tree.TrieNode

/**
 * Sensitive word filter
 * 敏感词过滤器
 * @author Toutatis_Gc
 */
class SensitiveWordFilter : AbstractWordSearchFilter() {

    /**
     * 排除词汇
     */
    private val excludeWords = HashSet<String>()

    override fun search(text: String?): MutableList<WordSearchResult> {
        val filteredWords: MutableList<WordSearchResult> = ArrayList()
        if (Validator.strIsBlank(text)) return filteredWords
        var currentWord = StringBuilder()
        var current: TrieNode<Char> = root
        var idx = 0
        val records = LinkedHashMap<String,ArrayList<Int>>()
        while (idx < text!!.toCharArray().size) {
            val currentCharacter = text[idx]
            val treeExist = current.childrenNodes().containsKey(currentCharacter)
            if (treeExist) {
                current = current.childrenNodes()[currentCharacter]!!
                currentWord.append(currentCharacter)
                if (current.isEndNode) {
                    val word = currentWord.toString()
                    val positionList: ArrayList<Int>
                    if (!records.containsKey(word)) {
                        positionList = ArrayList()
                        records[word] = positionList
                    }else{
                        positionList = records[word]!!
                    }
                    var i = idx - word.length
                    if (i <= 0) {i = 0}
                    positionList.add(i)
                }
            } else {
                if (currentWord.isNotEmpty()) { idx-- }
                currentWord = StringBuilder()
                current = root
            }
            idx++
        }
        records.forEach { (word, positionList) ->
            run {
                if (excludeWords.contains(word)) return@forEach
                filteredWords.add(WordSearchResult(positionList, word))
            }
        }
        return filteredWords
    }

    /**
     * 排除词汇
     */
    fun exclude(vararg words: String) {
        words.forEach {
            if (Validator.strIsBlank(it)) return@forEach
            excludeWords.add(it)
        }
    }

}