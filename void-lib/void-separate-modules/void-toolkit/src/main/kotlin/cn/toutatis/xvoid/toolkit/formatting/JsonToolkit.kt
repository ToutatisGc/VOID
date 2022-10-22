package cn.toutatis.xvoid.toolkit.formatting

import com.alibaba.fastjson.JSONObject
import org.slf4j.LoggerFactory

/**
 * @author Toutatis_Gc
 * @date 2022/4/17 12:53
 * Json Toolkit, currently only includes FastJSON support.
 */
object JsonToolkit {

    /**
     * Default maximum recorded value.
     */
    const val DEFAULT_MAX_RECORD = 16

    private var recordJsonHash:HashMap<String,JSONObject> = HashMap()

    private val logger = LoggerFactory.getLogger(JsonToolkit::class.java)

    /**
     * Maximum number of records. If this value is exceeded, the JSON map will be cleared and recorded again.
     */
    private var maxRecord = DEFAULT_MAX_RECORD

    private var alreadySetMaxRecord = false

    private var callRecordNumMethodClassName = ""

    /**
     * 此方法获取以分隔符(.) 分隔的键，向下迭代以获取值.
     *
     * This method gets the delimiter (.) Delimited keys, iterating down to get the value.
     *
     * example: 获取json对象"obj.key1.key2.key3"的值111
     * {
     *      key1:{
     *          key2: {
     *              key3: 111
     *          }
     *      }
     * }
     */
   @JvmStatic
    fun <T> getValue(obj : JSONObject, key: String, type:Class<T>): T? {
        if (obj.isEmpty()) return null
        //只有一个key直接返回值
        if (key.indexOf(".") == -1){
            logger.warn("Please use the getValue method of the native FastJson.JSONObject,this method is used to get value separated by a delimiter[.]")
            return obj.getObject(key,type)
        }
        //判断key的前缀,相同则查找同对象变量,省去迭代的过程
        val keySuffix = "${obj.hashCode()}-${key.substring(0, key.lastIndexOf("."))}"
        if (recordJsonHash.containsKey(keySuffix)){
            val footKey = key.substring(key.lastIndexOf(".") + 1, key.length)
            return recordJsonHash.getValue(keySuffix).getObject(footKey,type)
        }
        val split = key.split(".")
        //向下迭代对象
        var tmpJSONObject : JSONObject? = null
        val index = split.size - 1
        for (i in 0 until index) {
            val ks = split[i]
            tmpJSONObject = if (i == 0){
                obj.getJSONObject(ks) ?: throw NoSuchElementException("Please check object has this key [ $ks ]. ")
            }else{
                val jsonObject = tmpJSONObject!!.getJSONObject(ks) ?: throw NoSuchElementException("Please check object has this key [ $ks ]. ")
                // 存入最后一个对象
                if (i == index-1){
                    recordJsonHash[keySuffix] = jsonObject
                }
                jsonObject
            }
        }
        if (recordJsonHash.size > maxRecord){
            recordJsonHash.clear()
        }
        return tmpJSONObject!!.getObject(split[split.lastIndex],type)
    }

    @JvmStatic
    fun getInnerMap(): HashMap<String,JSONObject> {
        return recordJsonHash
    }

    @JvmStatic
    fun getString(obj : JSONObject, key: String): String? {
        return getValue(obj,key,String::class.java)
    }

    @JvmStatic
    fun getInteger(obj : JSONObject, key: String): Int? {
        return getValue(obj,key,Int::class.java)
    }

    @JvmStatic
    fun getBoolean(obj : JSONObject, key: String): Boolean? {
        return getValue(obj,key,Boolean::class.java)
    }

    /**
     * Change the maximum record value of recordJSONMap
     * @param num Allows the size of a map to be recorded
     */
    @JvmStatic
    fun setMaxRecordNum(num:Int): Unit {
        if (!alreadySetMaxRecord){
            callRecordNumMethodClassName = Thread.currentThread().stackTrace[2].className
            if (num <= 0){
                logger.warn("You should set a non-zero number to make large iterators more efficient.")
            }else if (num % 2 != 0){
                logger.warn("You should set this value to the NTH power of 2 for maximum efficiency.")
            }
            alreadySetMaxRecord = true
            maxRecord = num
        }else{
            logger.warn("This method can only be called once and cannot be modified.")
            logger.warn("Caller:$callRecordNumMethodClassName")
        }
    }
}

/**
 * Extend the getValue function of JSONObject to get iterated values.
 */
fun <T> JSONObject.getItValue(key: String, type:Class<T>): T? = JsonToolkit.getValue(this, key, type)

/**
 *Extend the getString function of JSONObject to get iterated values.
 */
fun JSONObject.getItString(key: String ): String? = JsonToolkit.getString(this, key)

fun JSONObject.getItString(key: String,default:String): String = JsonToolkit.getString(this, key) ?: default

/**
 *Extend the getInteger function of JSONObject to get iterated values.
 */
fun JSONObject.getItInteger(key: String): Int? = JsonToolkit.getInteger(this, key)

fun JSONObject.getItInteger(key: String,default:Int): Int = JsonToolkit.getInteger(this, key) ?: default

/**
 *Extend the getBoolean function of JSONObject to get iterated values.
 */
fun JSONObject.getItBoolean(key: String ): Boolean? = JsonToolkit.getBoolean(this, key)

fun JSONObject.getItBoolean(key: String,default:Boolean): Boolean = JsonToolkit.getBoolean(this, key) ?: default