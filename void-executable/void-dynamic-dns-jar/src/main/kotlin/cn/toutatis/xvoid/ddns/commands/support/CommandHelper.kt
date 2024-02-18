package cn.toutatis.xvoid.ddns.commands.support

import com.alibaba.fastjson.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.regex.Pattern

open class CommandHelper {

    companion object{

        val logger: Logger = LoggerFactory.getLogger("CMD")

        @JvmStatic
        fun analysisArgs(args:Any?): JSONObject {
            val argHash = JSONObject(true)
            if (args == null){
                return argHash
            }
            if (args is String){
                argHash[getParamName(args)] = true
            }else if (args is List<*>){
                args as List<String>
                if (args.size == 1){
                    argHash[getParamName(args[0])] = true
                }else if (args.size > 1){
                    val size = args.size
                    var index = 0
                    while (index < size){
                        val currentStr = args[index]
                        val isKey = currentStr.startsWith("-")
                        if (isKey){
                            val next:Any= try {
                                var final:Any = false
                                val s = args[index + 1] as Any
                                when(s.javaClass){
                                    String::class.java ->{
                                        s as String
                                        final = when (s) {
                                            s.startsWith("-") -> true
                                            Pattern.compile("^[-\\+]?[\\d]*$").matcher(s).matches() -> s.toInt()
                                            "true" -> true
                                            "false" -> false
                                            else -> s
                                        }
                                    }
                                }
                                final
                            }catch (e:IndexOutOfBoundsException){
                                index++
                                true
                            }
                            existKey(argHash, getParamName(currentStr),next)
                        }
                        index++
                    }
                }
            }
            return argHash
        }

        private fun getParamName(param:String): String {
            if (param.startsWith("-")){
                val replace = param.replace("-", "")
                if (replace != ""){
                    return replace
                }else{
                    throw IllegalFormatFlagsException("参数命名错误")
                }
            }else{
                throw IllegalArgumentException("错误参数格式")
            }
        }

        private fun existKey(argHash:JSONObject,key:String,value:Any?): Unit {
            if (!argHash.containsKey(key)){
                argHash[key] = value
            }
        }

    }

}