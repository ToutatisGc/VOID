package cn.toutatis.xvoid.toolkit.clazz

import kotlin.Throws
import cn.toutatis.xvoid.toolkit.clazz.XFunc
import cn.toutatis.xvoid.toolkit.clazz.LambdaToolkit
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.invoke.SerializedLambda

/**
 * @author Toutatis_Gc
 */
object LambdaToolkit {

    private const val GET_FIELD_LAMBDA = "get"

    /**
     * 将指定的函数对象转换为 SerializedLambda 对象，以便在序列化或其他操作中使用。
     *
     * @param func 一个实现了函数接口的函数对象，用于进行序列化
     * @return 返回一个 SerializedLambda 对象，表示序列化后的函数对象
     * @throws Exception 如果在序列化过程中发生异常，则抛出 Exception 异常
     */
    @JvmStatic
    @Throws(Exception::class)
    fun <T, R> serialize(func: XFunc<*, *>): SerializedLambda {
        val writeReplace = func.javaClass.getDeclaredMethod("writeReplace")
        writeReplace.isAccessible = true
        val sl = writeReplace.invoke(func)
        return sl as SerializedLambda
    }

    /**
     * 从 SerializedLambda 对象中提取并返回字段名。
     *
     * @param serializedLambda 一个包含 Lambda 表达式信息的 SerializedLambda 对象
     * @return 返回从 Lambda 表达式中提取的字段名
     * @throws IllegalArgumentException 如果 Lambda 表达式的实现方法不以 "get" 开头，则抛出 IllegalArgumentException 异常
     */
    @JvmStatic
    fun getFieldName(serializedLambda: SerializedLambda): String {
        val implMethodName = serializedLambda.implMethodName
        val capturingClass = Class.forName(serializedLambda.capturingClass.replace("/","."))
        require(serializedLambda.implMethodName.startsWith(GET_FIELD_LAMBDA)) { "非GET参数" }
        val uppercaseName = implMethodName.substring(3)
        val firstChar = Character.toLowerCase(uppercaseName[0])
        val lowercaseFieldName = firstChar.toString() + uppercaseName.substring(1)
        var fieldName:String
        try {
            // TODO 解析字段
            val field = capturingClass.getField(lowercaseFieldName)
//            field.getAnnotation()
            fieldName = lowercaseFieldName;
        }catch (exception:NoSuchMethodException){
            fieldName = lowercaseFieldName;
        }
        return fieldName
    }

    /**
     * 将lambda表达式使用表达式转换为字符串字段
     * @param func 需要转换的字段
     * @return 表达式字段
     */
    @JvmStatic
    @Throws(Exception::class)
    fun getFieldName(func: XFunc<*, *>): String {
        return getFieldName(serialize<Any, Any>(func))
    }
}