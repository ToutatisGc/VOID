package cn.toutatis.creeper

import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 * @author Toutatis_Gc
 * @date 2022/8/20 12:45
 * 网页文档解析器
 * TODO 爬虫待完善
 */
object DocumentResolver {

    fun resolve(html: String): Unit {
        val connect = Jsoup.connect(html).get()
        val titles: Elements = connect.getElementsByClass("review-short")
        var index = 1
        for (title in titles) {
            val text: String = title.text()
            // 过滤掉电影的其他名称
            if (!text.contains("/")) {
                println("No.$index $text")
                index++
            }
        }
    }

}