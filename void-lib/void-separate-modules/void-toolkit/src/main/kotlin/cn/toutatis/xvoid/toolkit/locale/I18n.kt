package cn.toutatis.xvoid.toolkit.locale

import cn.toutatis.xvoid.toolkit.Meta
import cn.toutatis.xvoid.toolkit.file.FileToolkit
import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit
import cn.toutatis.xvoid.toolkit.log.warnWithModule
import cn.toutatis.xvoid.toolkit.validator.Validator
import com.alibaba.fastjson.JSONObject
import java.io.File
import java.util.*

/**
 * 国际化工具
 * 面对不同的语言环境应当采用不同的语言提示
 * 默认调用resources下的i18n文件夹下的本地化翻译文件夹的json
 * @author Toutatis_Gc
 */
object I18n {

    private val logger = LoggerToolkit.getLogger(this.javaClass)
    /**
     * 初始化设置区域类型为SIMPLIFIED_CHINESE
     */
    init {
        setLocale(Locale.SIMPLIFIED_CHINESE)
    }

    /**
     * 区域信息
     * 如果在框架中应当以框架配置为准
     * 调用setter方法修正区域信息
     */
    private lateinit var locale: Locale

    /**
     * 将设置区域所加载的对象存入此处,翻译依赖对象都在此位置
     */
    private lateinit var translationObject:JSONObject

    /**
     * 设置区域信息并加载语言库
     */
    @JvmStatic
    fun setLocale(locale: Locale){
        this.locale = locale
        this.loadLanguageLib(locale)
    }

    /**
     * 从库中翻译
     */
    @JvmStatic
    fun translate(key:String): String {
        return translate(key,null)
    }

    /**
     * 从库中翻译
     */
    @JvmStatic
    fun translate(key:String,vararg args:String?): String {
        if (Validator.strIsBlank(key)){ return "" }
        val transStr = JsonToolkit.getString(translationObject, key.trim().lowercase())
        return if (transStr != null){
            if (args.isEmpty()){ return transStr }
            transStr.format(args)
        }else{ "" }
    }

    /**
     * 根据区域信息加载语言库
     * ISSUE: 目前只支持CN/US
     */
    private fun loadLanguageLib(locale: Locale) {
        var country = ""
        if (locale.country.isEmpty()) when (locale.language) {
            "zh" -> country = "CN"
            "en" -> country = "US"
        } else country = locale.country
        val filename = "i18n/locale_${locale.language}_${country}.json"
        val path = FileToolkit.getResourceFile(filename)?.path
        if (Validator.strIsBlank(path)) {
            logger.warnWithModule(Meta.MODULE_NAME,
                "Locale translation file not found [${filename}]." +
                        "[SIMPLIFIED_CHINESE] will be used by default locale.")
            this.locale = Locale.SIMPLIFIED_CHINESE
            loadLanguageLib(locale)
            return
        }
        val parseJsonObject = JsonToolkit.parseJsonObject(File(path!!))
        translationObject = parseJsonObject
    }

}