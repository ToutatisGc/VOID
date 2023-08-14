package cn.toutatis.redis

import cn.toutatis.xvoid.common.standard.StandardFields

/**
 * Redis common keys Redis常用键
 *
 * @constructor Create empty Redis common keys
 */
object RedisCommonKeys {

    /**
     * Session Key HttpSessionId键
     */
    const val SESSION_KEY = "${StandardFields.SYSTEM_PREFIX}.SESSION.%s"

    @JvmStatic
    fun concat(key:String,vararg value:Any?):String{
        return String.format(key,*value)
    }

}