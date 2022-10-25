package cn.toutatis.xvoid.toolkit.formatting

import com.alibaba.fastjson.JSONObject
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

/**
 * @author Toutatis_Gc
 * @date 2022/10/25 15:11
 * XML工具类
 */
object XmlToolkit {

    /**
     * @param xml xml字符串
     * @return xml转换为JsonObject
     */
    @JvmStatic
    fun xmlToMap(xml: String): JSONObject? {
        return try {
            val data = JSONObject(true)
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val stream: InputStream = ByteArrayInputStream(xml.toByteArray(StandardCharsets.UTF_8))
            val doc = documentBuilder.parse(stream)
            doc.documentElement.normalize()
            val nodeList = doc.documentElement.childNodes
            for (idx in 0 until nodeList.length) {
                val node = nodeList.item(idx)
                if (node.nodeType == Node.ELEMENT_NODE) {
                    val element = node as Element
                    data[element.nodeName] = element.textContent
                }
            }
            stream.close()
            data
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     */
    @JvmStatic
    fun mapToXml(data: JSONObject): String? {
        return try {
            val documentBuilderFactory = DocumentBuilderFactory.newInstance()
            val documentBuilder = documentBuilderFactory.newDocumentBuilder()
            val document = documentBuilder.newDocument()
            val root = document.createElement("xml")
            document.appendChild(root)
            for (key in data.keys) {
                val value = data[key]
                val filed = document.createElement(key)
                val node: Node = if (value?.javaClass == String::class.java) {
                    document.createCDATASection(value.toString())
                } else {
                    document.createTextNode(value.toString())
                }
                filed.appendChild(node)
                root.appendChild(filed)
            }
            val tf = TransformerFactory.newInstance()
            val transformer = tf.newTransformer()
            val source = DOMSource(document)
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            val writer = StringWriter()
            val result = StreamResult(writer)
            transformer.transform(source, result)
            //.replaceAll("\n|\r", "");
            val output = writer.buffer.toString()
            writer.close()
            output
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}