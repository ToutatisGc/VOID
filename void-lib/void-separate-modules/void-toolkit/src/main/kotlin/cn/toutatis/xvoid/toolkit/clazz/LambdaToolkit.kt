package cn.toutatis.xvoid.toolkit.clazz

import cn.toutatis.xvoid.common.annotations.database.AssignField
import cn.toutatis.xvoid.toolkit.validator.Validator
import java.lang.invoke.SerializedLambda

/**
 * @author Toutatis_Gc
 */
object LambdaToolkit {

    private val CACHE:HashMap<String,String> = HashMap(128)

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
        require(
            implMethodName.startsWith(ReflectToolkit.GET_FIELD_LAMBDA) ||
                implMethodName.startsWith(ReflectToolkit.IS_FIELD_LAMBDA)
        ) { "非Getter字段" }
        val implClassName = serializedLambda.implClass
        val keyName = "${implClassName}:${implMethodName}"
        if (CACHE.containsKey(keyName)){
            return CACHE[keyName]!!
        }
        val implClass = Class.forName(implClassName.replace("/","."))
        val uppercaseName = implMethodName.substring(if(implMethodName.startsWith(ReflectToolkit.GET_FIELD_LAMBDA)) 3 else 2)
        val firstChar = Character.toLowerCase(uppercaseName[0])
        val lowercaseFieldName = firstChar.toString() + uppercaseName.substring(1)
        val fieldName:String = try {
            val declaredField = implClass.getDeclaredField(lowercaseFieldName)
            val ddlField = declaredField.getDeclaredAnnotation(AssignField::class.java)
            if (ddlField != null){
                return if (Validator.strIsBlank(ddlField.name)) lowercaseFieldName else ddlField.name
            }else{ lowercaseFieldName }
        }catch (exception:NoSuchMethodException){
            lowercaseFieldName;
        }
        CACHE[keyName] = fieldName
        return fieldName
    }

    /**
     * 将lambda表达式使用表达式转换为字符串字段
     * @param func 需要转换的字段
     * @return 表达式字段
     */
    @JvmStatic
    fun <T,R> getFieldName(func: XFunc<T, R>): String {
        return getFieldName(serialize<T, R>(func))
    }

}