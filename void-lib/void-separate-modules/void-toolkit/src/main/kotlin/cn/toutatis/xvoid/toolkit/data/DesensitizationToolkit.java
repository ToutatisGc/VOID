package cn.toutatis.xvoid.toolkit.data;


import cn.hutool.core.util.ReflectUtil;
import cn.toutatis.xvoid.toolkit.Meta;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import cn.toutatis.xvoid.toolkit.validator.Validator;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>信息脱敏工具</p>
 * <p>对于身份证手机号等敏感信息,默认前端展示时需要隐藏部分内容</p>
 * <p>支持list,map</p>
 * <p>例如手机号11位数177****0355隐藏中间四位数</p>
 * @author Tutatis_Gc
 */
public class DesensitizationToolkit {

    /**
     * 批量脱敏数据
     * @param list 需要脱敏的list
     * @param fields 脱敏字段
     * @param <P> 类型泛型
     * @return 脱敏后的数据
     */
    public static <P> List<P> hiddenInfo(List<P> list,String... fields){
        if (list == null || list.isEmpty()){return list;}
        if (fields.length == 0){return list;}
        ArrayList<P> pl = new ArrayList<>(list.size());
        for (P p : list) {
            if (p instanceof Map){
                Map<String, Object> hm = hiddenInfo((Map<String, Object>) p, fields);
                pl.add((P) hm);
            }else{
                Map<String, Field> fieldMap = ReflectUtil.getFieldMap(p.getClass());
                for (String field : fields) {
                    if (fieldMap.containsKey(field)){
                        Field objField = fieldMap.get(field);
                        objField.setAccessible(true);
                        try {
                            objField.set(p,hiddenInfo(objField.get(p).toString()));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                pl.add(p);
            }
        }
        return pl;
    }

    /**
     * <p>map对象字段信息脱敏,脱敏字段需要为基础类型或基础类型的包装类</p>
     * <p>复杂类型对象则不可转换</p>
     * <p><b>注意:字段会转换成string类型</b></p>
     * @param map map对象
     * @param fields 需要脱敏字段
     * @return 已脱敏数据
     */
    public static Map<String,Object> hiddenInfo(Map<String,Object> map ,String... fields){
        if (map == null || map.isEmpty()){ return map; }
        if (fields.length == 0){return map;}
        for (String field : fields) {
            if (map.containsKey(field)){
                Class<?> fieldClass = map.get(field).getClass();
                boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(fieldClass);
                if (!primitiveOrWrapper){
                    throw new IllegalArgumentException(LoggerToolkit.infoWithModule(Meta.MODULE_NAME, "脱敏字段需要基础类型或基础包装类"));
                }
                String hiddenInfo = hiddenInfo(String.valueOf(map.get(field)));
                map.put(field,hiddenInfo);
            }else {
                throw new IllegalArgumentException(LoggerToolkit.infoWithModule(Meta.MODULE_NAME, "脱敏map不存在[%s]字段".formatted(field)));
            }
        }
        return map;
    }

    /**
     * <p>脱敏方法,该方法的根据字符串长度隐藏中间部分敏感信息</p>
     * <p>通用解决方案，将字符串拆分成三部分，隐藏中间部分打*号</p>
     * @param info 需要隐藏敏感的字符串
     * @return 脱敏信息
     */
    public static String hiddenInfo(String info){
        return hiddenInfo(info,"*");
    }

    /**
     * 脱敏方法,该方法的根据字符串长度隐藏中间部分敏感信息
     * @param info 需要隱藏的信息
     * @param customSymbol 自定义脱敏符号(一般推荐使用*)
     * @return 脱敏信息
     */
    public static String hiddenInfo(String info,String customSymbol){
        if (Validator.strIsBlank(info)){return null;}
        if (customSymbol == null || customSymbol.length() > 1){
            throw new IllegalArgumentException(LoggerToolkit.infoWithModule(Meta.MODULE_NAME, "脱敏标识符不得为空或者大于1个字符"));
        }
        String trimInfo = info.trim();
        int infoLength = trimInfo.length();
        if (infoLength == 0){ return trimInfo; }
        if (infoLength == 1){ return customSymbol;}
        if (infoLength == 2){ return trimInfo.charAt(0) + customSymbol; }
        if (infoLength == 3){ return trimInfo.charAt(0) + customSymbol + trimInfo.charAt(2); }
        else{
            boolean isEvenLengthStr = infoLength % 2 == 0;
            int hiddenLength = infoLength / 3;
            StringBuilder sb = new StringBuilder();
            int startLength = infoLength - 2* hiddenLength;
            if (!isEvenLengthStr) {startLength--;}
            int i = 0;
            while (i < startLength) { sb.append(customSymbol);i++; }
            return trimInfo.substring(0, hiddenLength) + sb.toString() + trimInfo.substring((infoLength-hiddenLength)+(isEvenLengthStr?0:-1),infoLength);
        }
    }

}
