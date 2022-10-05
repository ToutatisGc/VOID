package cn.toutatis.xvoid.toolkit.data;


import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Tutatis_Gc
 * 信息脱敏工具
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
     * @param map map数据
     * @param fields 脱敏字段
     * @return 脱敏数据
     */
    public static Map<String,Object> hiddenInfo(Map<String,Object> map ,String... fields){
        if (map == null || map.isEmpty()){ return map; }
        if (fields.length == 0){return map;}
        for (String field : fields) {
            if (map.containsKey(field)){
                map.put(field,hiddenInfo((String) map.get(field)));
            }
        }
        return map;
    }

    /**
     * 通用解决方案，将字符串拆分成三部分，隐藏中间部分打*号
     * @param info 需要隐藏敏感的字符串
     * @return 脱敏信息
     */
    public static String hiddenInfo(String info){
        return hiddenInfo(info,"*");
    }

    /**
     * @param info 需要隱藏的信息
     * @param customSymbol 自定义符号
     * @return 脱敏信息
     */
    public static String hiddenInfo(String info,String customSymbol){
        if (info == null || "".equals(info) || info.length() == 0){return null;}
        String trimInfo = info.trim();
        int length = trimInfo.length();
        if (length == 1){return "*";}
        if (length == 2){ return trimInfo.charAt(0) + customSymbol; }
        if (length == 3){ return trimInfo.charAt(0) + customSymbol + trimInfo.charAt(2); }
        else{
            boolean o = length % 2 == 0;
            int hiddenLength = length / 3;
            StringBuilder sb = new StringBuilder();
            int startLength = length - 2* hiddenLength;
            if (!o) {startLength--;}
            int i = 0;
            while (i < startLength) { sb.append(customSymbol);i++; }
            return trimInfo.substring(0, hiddenLength)
                    + sb.toString()
                    + trimInfo.substring((length-hiddenLength)+(o?0:-1),length);
        }
    }

}
