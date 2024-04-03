package cn.toutatis.xvoid.toolkit.data

import cn.toutatis.xvoid.achievable.filter.AbstractWordSearchFilter
import cn.toutatis.xvoid.achievable.filter.WordSearchResult
import cn.toutatis.xvoid.structure.tree.TrieNode

/**
 * 自动补全过滤器
 * @author Toutatis_Gc
 */
class ContentAutoCompleteFilter : AbstractWordSearchFilter() {

    override fun search(text: String?): MutableList<WordSearchResult> {
        val suggestions: MutableList<WordSearchResult> = ArrayList()
        if (text.isNullOrEmpty()) { return suggestions }
        var current: TrieNode<Char> = root
        for (c in text.toCharArray()) {
            if (!current.childrenNodes().containsKey(c)) {
                return ArrayList()
            }
            current = current.childrenNodes()[c]!!
        }
        autocomplete(current, StringBuilder(text), suggestions)
        return suggestions
    }

    private fun autocomplete(node: TrieNode<Char>, word: StringBuilder, suggestions: MutableList<WordSearchResult>) {
        if (node.isEndNode) {
            suggestions.add(WordSearchResult(null, word.toString()))
        }
        for (c in node.childrenNodes().keys) {
            autocomplete(node.childrenNodes()[c]!!, word.append(c), suggestions)
            word.deleteCharAt(word.length - 1)
        }
    }


}