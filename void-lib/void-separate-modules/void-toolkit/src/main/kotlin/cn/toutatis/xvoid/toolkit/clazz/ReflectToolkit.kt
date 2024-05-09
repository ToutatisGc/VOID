package cn.toutatis.xvoid.toolkit.clazz

import cn.toutatis.xvoid.common.annotations.database.AssignField
import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*


/**
 * Reflect toolkit
 * 反射相关工具
 * @constructor Create empty Reflect toolkit
 */
object ReflectToolkit {

    /**
     * Getter描述符
     */
    const val IS_FIELD_LAMBDA = "is"
    const val GET_FIELD_LAMBDA = "get"

    /**
     * Setter描述符
     */
    const val SET_FIELD_LAMBDA = "set"

    /**
     * Convert a map to entity
     * 将Map类型对象转换为Java实体类
     * @param T 泛型
     * @param map 需要转换的图
     * @param entityClass 实体类Class
     * @return 转换实体类
     */
    @JvmStatic
    @Throws(Exception::class)
    fun <T> convertMapToEntity(map: Map<String?, Any?>, entityClass: Class<T>): T {
        // 获取实体类构造
        val t = entityClass.getDeclaredConstructor().newInstance()
        val fields = entityClass.declaredFields
        // 遍历对象字段
        for (field in fields) {
            // 判断是否为静态字段,跳过静态字段
            if(Modifier.isStatic(field.modifiers)){ continue }
            field.isAccessible = true
            // 获取字段上注解
            val assignField = field.getDeclaredAnnotation(AssignField::class.java)
            val fieldName = assignField?.name ?: field.name
            // 判断字段类型是否为基本类型获包装类型
            if (field.type.isPrimitive || isWrapperClass(field.type)){
                field[t] = map[fieldName]
            }else{
                // 如果是实体类型,将map中对象转为实体,并赋予当前类型
                val info = map[fieldName]
                if (info != null){
                    val parseJsonObject = JsonToolkit.parseJsonObject(info)
                    field[t] = this.convertMapToEntity(parseJsonObject,field.type)
                }
            }
        }
        return t
    }

    /**
     * Is wrapper class
     * 判断是否为包装类型
     * @param clazz class信息
     * @return 是否为包装类
     */
    @JvmStatic
    fun isWrapperClass(clazz: Class<*>): Boolean {
        return clazz == Integer::class.java || clazz == String::class.java ||
               clazz == Long::class.java || clazz == Short::class.java ||
               clazz == Byte::class.java || clazz == Float::class.java ||
               clazz == Double::class.java || clazz == Char::class.java ||
               clazz == Boolean::class.java
    }

    /**
     * Get all fields
     * 获取一个类的所有字段包括父级
     * @param clazz 类对象
     * @param includeSuper 包含父级
     * @return 所有Field字段
     */
    @JvmStatic
    fun getAllFields(clazz: Class<*>,includeSuper:Boolean = true): List<Field> {
        val fields = ArrayList<Field>()

        // 获取当前类的字段
        val declaredFields: Array<Field> = clazz.getDeclaredFields()
        for (field in declaredFields) {
            fields.add(field)
        }

        if (!includeSuper) return fields

        // 递归获取父类的字段
        val superClass = clazz.superclass
        if (superClass != null) {
            val superClassFields: List<Field> = getAllFields(superClass)
            fields.addAll(superClassFields)
        }
        return fields
    }

    /**
     * Set object field
     * 设置对象字段
     * @param obj 对象
     * @param field 字段
     * @param value 值
     */
    @JvmStatic
    fun setObjectField(obj: Any, field: Field, value: Any){
        field.setAccessible(true)
        try {
            field.set(obj, value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Convert getter to field name
     * 将getter方法名转换为字段名
     * @param getterName getter方法名
     * @return 字段名
     */
    @JvmStatic
    fun convertGetterToFieldName(getterName: String): String {
        // 判断getter方法是否以"get"或"is"开头
        val fieldName = if (getterName.startsWith(GET_FIELD_LAMBDA)) {
            // 获取字段名的部分（去掉"get"后的字符串）
            getterName.substring(GET_FIELD_LAMBDA.length)
        } else if (getterName.startsWith(IS_FIELD_LAMBDA)) {
            // 获取字段名的部分（去掉"is"后的字符串）
            getterName.substring(IS_FIELD_LAMBDA.length)
        }else{
            throw IllegalArgumentException("The getter method name must start with \"get\" or \"is\"")
        }
        return fieldName[0].lowercaseChar().toString() + fieldName.substring(1)
    }

    /**
     * 获取一个类的所有getter方法
     */
    @JvmStatic
    fun getGetterMethods(clazz:Class<*>) : ArrayList<Method>{
        val methods = ArrayList<Method>()
        clazz.declaredMethods.forEach {
            val methodName = it.name
            if (methodName.startsWith(GET_FIELD_LAMBDA) || methodName.startsWith(IS_FIELD_LAMBDA)){
                if (methodName != "getClass") methods.add(it)
            }
        }
        return methods
    }

    /**
     * 获取字段的getter方法名
     */
    @JvmStatic
    fun getFieldGetterMethodName(field:Field):String{
        return getFieldGetterMethodName(field.name)
    }

    /**
     * 获取字段的getter方法名
     */
    @JvmStatic
    fun getFieldGetterMethodName(field:String?):String{
        if (field.isNullOrEmpty()){
            throw IllegalArgumentException("The field name cannot be empty")
        }
        return GET_FIELD_LAMBDA + field[0].uppercaseChar() + field.substring(1)
    }

    /**
     * 获取字段的getter方法名
     */
    @JvmStatic
    fun getFieldSetterMethodName(field:Field):String{
        return getFieldSetterMethodName(field.name)
    }

    /**
     * 获取字段的setter方法名
     */
    @JvmStatic
    fun getFieldSetterMethodName(field:String?):String{
        if (field.isNullOrEmpty()){
            throw IllegalArgumentException("The field name cannot be empty")
        }
        return SET_FIELD_LAMBDA + field[0].uppercaseChar() + field.substring(1)
    }

            /**
     * 获取字段的值
     */
    @JvmStatic
    fun getFieldValue(obj: Any, field: Field):Any?{
        field.isAccessible = true
        return try {
            field.get(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 递归获取字段
     * @param clazz 类
     * @param fieldName 字段名
     */
    @JvmStatic
    fun recursionGetField(clazz: Class<*>, fieldName: String): Field? {
        return recursionGetField(clazz, fieldName,true)
    }

    /**
     * 递归获取字段,不包含父类
     * @param clazz 类
     * @param fieldName 字段名
     */
    @JvmStatic
    fun recursionGetFieldExcludeSuper(clazz: Class<*>, fieldName: String): Field? {
        return recursionGetField(clazz, fieldName,false)
    }

    /**
     * 递归获取字段
     * @param clazz 类
     * @param fieldName 字段名
     * @param includeSuper 是否包含父类
     */
    @JvmStatic
    fun recursionGetField(clazz: Class<*>, fieldName: String,includeSuper: Boolean): Field? {
        return try {
            clazz.getDeclaredField(fieldName)
        } catch (e: NoSuchFieldException) {
            if (!includeSuper) return null
            if (clazz.superclass != null) {
                recursionGetField(clazz.superclass, fieldName,true)
            } else null
        }
    }

    @JvmStatic
    fun invokeFieldGetter(field: Field, obj: Any,vararg args:Any?):Any?{
        return invokeFieldGetter(field.name,obj,*args)
    }

    @JvmStatic
    fun invokeFieldGetter(field: String, obj: Any,vararg args:Any?):Any?{
        return invokeFieldGetter(field,obj, ClassToolkit.castObjectArray2ClassArray(listOf(*args)),*args)
    }

    @JvmStatic
    fun invokeFieldGetter(field: Field, obj: Any,methodParameterClass: Array<Class<*>>,vararg args:Any?):Any?{
        return invokeFieldGetter(field.name,obj, methodParameterClass,*args)
    }

    @JvmStatic
    fun invokeFieldGetter(field: String, obj: Any,methodParameterClass: Array<Class<*>>,vararg args:Any?):Any?{
        return invokeMethod(getFieldGetterMethodName(field),obj, methodParameterClass,*args)
    }

    @JvmStatic
    fun invokeFieldSetter(field: Field, obj: Any,vararg args:Any?):Any?{
        return invokeFieldSetter(field.name,obj,*args)
    }

    @JvmStatic
    fun invokeFieldSetter(field: String, obj: Any,vararg args:Any?):Any?{
        return invokeFieldSetter(field,obj, ClassToolkit.castObjectArray2ClassArray(listOf(*args)),*args)
    }

    @JvmStatic
    fun invokeFieldSetter(field: Field, obj: Any,methodParameterClass: Array<Class<*>>,vararg args:Any?):Any?{
        return invokeFieldSetter(field.name,obj, methodParameterClass,*args)
    }

    @JvmStatic
    fun invokeFieldSetter(field: String, obj: Any,methodParameterClass: Array<Class<*>>,vararg args:Any?):Any?{
        return invokeMethod(getFieldSetterMethodName(field),obj, methodParameterClass,*args)
    }

    /**
     * 静态方法，用于调用对象上的指定方法。
     *
     * @param methodName 要调用的方法的名称。
     * @param obj 要调用方法的对象实例。
     * @param methodParameterClass 方法参数的类型数组。
     * @param args 调用方法时传递的参数，可变参数。
     * @return 方法的返回值，如果方法无返回值则返回null。
     */
    @JvmStatic
    fun invokeMethod(methodName: String, obj: Any, methodParameterClass: Array<Class<*>>, vararg args:Any?):Any?{
        return obj::class.java.getMethod(methodName, *methodParameterClass).invoke(obj,*args)
    }

}

fun Any.invokeGetter(field: String,vararg args:Any?):Any?{
    return ReflectToolkit.invokeFieldGetter(field,this,*args)
}
