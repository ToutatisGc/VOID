package cn.toutatis.xvoid.toolkit.formatting;

/**
 * @author Toutatis_Gc
 * @date 2022/10/26 21:24
 * 枚举工具类
 */
public class EnumToolkit {

    public static <T extends Enum<T>> T getValue(Class<T> clazz , String name) throws IllegalArgumentException{
        return getValue(clazz,name,true);
    }

    /**
     * @param clazz 转换类
     * @param name 字面值
     * @param nullIsNormal 无字面值可以返回null
     * @param <T> 泛型
     * @return 字符串转枚举值
     * @throws IllegalArgumentException 枚举无字面值
     */
    public static <T extends Enum<T>> T getValue(Class<T> clazz , String name,Boolean nullIsNormal) throws IllegalArgumentException {
        try {
            return T.valueOf(clazz,name.toUpperCase());
        }catch (IllegalArgumentException e){
            if (nullIsNormal){
                return null;
            }else{
                throw e;
            }
        }
    }

}
