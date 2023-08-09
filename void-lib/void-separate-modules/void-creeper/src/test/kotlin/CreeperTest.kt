import cn.toutatis.creeper.DocumentResolver
import cn.toutatis.xvoid.toolkit.constant.Time
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test
import org.wltea.analyzer.lucene.IKAnalyzer
import java.io.StringReader
import java.util.*
import kotlin.collections.HashSet


/**
 * @author Toutatis_Gc
 * @date 2022/8/20 12:48
 *
 */

class CreeperTest {

    @Test
    fun test() {
        DocumentResolver.resolve("https://movie.douban.com/review/best/?start=0")
    }

    private val url = "http://www.ce.cn/"

    private val lookOnlyToday = true

    private val keywords = HashSet<String>()

    @Test
    fun commonCreeper(){
        keywords.add("企业")
        // 最终返回对象
        val info = JSONObject(true)
        info["url"] = url
        // 获取页面数据
        val document: Document = Jsoup.connect(url).get()
        // 获取标题,确认连接成功
        val title = document.title()
        info["title"] = title
        // 爬取正文
        val searchQuery:String
        if (lookOnlyToday){
            val date = Date()
            val regexTime = Time.regexTime(Time.YMD_NON_SEPARATOR_TIME,date)
            searchQuery="a[href*=${regexTime}]"
        }else{
            searchQuery = "a[href]"
        }
        val ikAnalyzer = IKAnalyzer(true)
        var tokenStream: TokenStream
        val elements = document.select(searchQuery)
        val articles = JSONArray()
        for (i in 0 until elements.size){
            val element = elements[i]
            val text = element.text()
            if (text.isEmpty()) continue
            val article = JSONObject(true)
            article["articleTitle"] = text
            try {
                val stringReader = StringReader(text)
                tokenStream = ikAnalyzer.tokenStream("", stringReader)
                tokenStream.reset()
                val term: CharTermAttribute = tokenStream.getAttribute(CharTermAttribute::class.java)
                //遍历分词数据
                val words = HashSet<String>()
                while (tokenStream.incrementToken()) {
                    val word = term.toString()
                    if (word.length > 1) words.add(word)
                }
                stringReader.close()
                tokenStream.close()
                article["seg"] = words
            }catch (e:Exception){
                System.err.println(e)
            }
            article["href"] = element.attr("href")
            articles.add(article)
        }
        info["articles"] = articles
        System.err.println(info.toString())
        val message = JSONObject()
        message["msgtype"] = "markdown"
        val markdown = JSONObject()
        var str = "## 今日电子报 \n"
        for (i in 0 until 10){
            val art = articles[i] as JSONObject
            str+="[${art["articleTitle"]}](${art["href"]})\n"
        }
        markdown["content"] = str
        message["markdown"] = markdown
//        System.err.println(message.toString(SerializerFeature.PrettyFormat))
    }

    @Test
    fun testHttps(){
        val document = Jsoup.connect("http://gov.sina.com.cn/").get()
        System.err.println(document.select("a[href*=2023-08-09]"))
    }

}