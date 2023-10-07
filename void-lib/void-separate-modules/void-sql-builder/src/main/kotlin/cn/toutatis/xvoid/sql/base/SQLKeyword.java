package cn.toutatis.xvoid.sql.base;

import cn.toutatis.xvoid.toolkit.validator.Validator;

/**
 * SQL 关键字枚举
 * 用来判断是否有SQL字段占用关键字并转义
 * @author Toutatis_Gc
 */
public enum SQLKeyword {
    /**
     * 关键字可自行扩展
     */
    ALL,
    ALTER,
    AND,
    AS,
    ASC,
    AVG,
    BETWEEN,
    BY,
    CASE,
    COUNT,
    CREATE,
    DELETE,
    DESC,
    DISTINCT,
    DROP,
    ELSE,
    END,
    EXISTS,
    FROM,
    GROUP,
    HAVING,
    IN,
    INNER,
    INSERT,
    IS,
    JOIN,
    LEFT,
    LIKE,
    MAX,
    MIN,
    NOT,
    NULL,
    ON,
    OR,
    ORDER,
    OUTER,
    RIGHT,
    SELECT,
    SUM,
    TABLE,
    THEN,
    UNION,
    UPDATE,
    VIEW,
    WHEN,
    WHERE;

    /**
     * 判断字符串是否匹配SQL关键字
     * @param value 字符串
     * @return 匹配结果
     */
    public static boolean match(String value){
        if (Validator.strNotBlank(value)){
            String tmp = value.toUpperCase();
            try {
                SQLKeyword.valueOf(tmp);
                return true;
            }catch (IllegalArgumentException e){
                return false;
            }
        }else {
            return false;
        }
    }

    /**
     * 判断字符串是否不匹配SQL关键字
     * @param value 字符串
     * @return 匹配结果
     */
    public static boolean notMatch(String value){
        return !match(value);
    }
}