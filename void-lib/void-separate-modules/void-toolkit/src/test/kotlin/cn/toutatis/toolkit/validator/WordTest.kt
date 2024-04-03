package cn.toutatis.toolkit.validator

import cn.toutatis.xvoid.toolkit.data.ContentAutoCompleteFilter
import cn.toutatis.xvoid.toolkit.validator.XvoidWords
import org.junit.Test

class WordTest {

    @Test
    fun testSensitiveWordFilter() {
        val builtInSensitiveWordFilter = XvoidWords.getBuiltInSensitiveWordFilter()
        val search = builtInSensitiveWordFilter.search("根据英国警方官网消息，当地时间3月20日23时15分，警方接到报警称在伦敦刘易舍姆庄园公园某处发生一起持刀伤人事件。警方和医护人员抵达后，发现现场有一名31岁女性身受重伤，尽管医护人员第一时间进行了抢救，但该女性仍因伤势过重不幸身亡。\n" +
                "3月22日，24岁的约书亚·麦克斯（Joshua Michals）因涉嫌谋杀被相关部门起诉。他于3月23日在布罗姆利地方法院被羁押，随后于3月26日在中央刑事法院出庭。下一次听证会将于6月11日举行，审判定于2025年1月进行。\n" +
                "3月28日，一位IP地址显示为山东的网友自称是被害人家属，她发帖求助称希望有在英留学生能够协助其家属处理后事。")
        System.err.println(search)
        System.err.println(builtInSensitiveWordFilter.trieTree().countNodes())
//        Assert.assertEquals(2, search.size)
    }

    @Test
    fun testWordFilter2() {
        val contentAutoCompleteFilter = ContentAutoCompleteFilter()
        contentAutoCompleteFilter.addSearchWord("苹果");
        contentAutoCompleteFilter.addSearchWord("苹果14");
        contentAutoCompleteFilter.addSearchWord("苹");
        contentAutoCompleteFilter.addSearchWord("应用");
        contentAutoCompleteFilter.addSearchWord("香蕉");
        contentAutoCompleteFilter.addSearchWord("蝙蝠");
        val search = contentAutoCompleteFilter.search("苹")
        System.err.println(search)
    }

}