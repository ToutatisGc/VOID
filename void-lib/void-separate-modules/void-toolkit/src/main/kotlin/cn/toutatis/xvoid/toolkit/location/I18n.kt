package cn.toutatis.xvoid.toolkit.location

import java.util.*

/**
 * 国际化工具
 * 面对不同的语言环境应当采用不同的语言提示
 * 默认调用resources下的i18n文件夹下的本地化翻译文件夹的json
 * @author Toutatis_Gc
 */
object I18n {

    /**
     * 区域信息
     * 如果在框架中应当以框架配置为准
     * 调用setter方法修正区域信息
     */
    private var locale: Locale = Locale.CHINA

    /**
     * 设置区域信息
     */
    @JvmStatic
    fun setLocale(locale: Locale){
        this.locale = locale
    }

    /**
     * 从库中翻译
     */
    @JvmStatic
    fun translate(key:String): Unit {
        // TODO 翻译
    }

}