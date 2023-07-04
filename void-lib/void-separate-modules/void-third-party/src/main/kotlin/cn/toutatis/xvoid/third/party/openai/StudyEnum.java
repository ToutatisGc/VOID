package cn.toutatis.xvoid.third.party.openai;

import cn.toutatis.xvoid.toolkit.log.LogEnum;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum StudyEnum {

    /**
     *
     */
    @LogEnum(open = true)
    @Cost(min = 10.0, max = 99.0)
    JAVA("java", "java基础"),

    @LogEnum(open = true)
    @Cost(min = 20.0, max = 79.0)
    PHP("php", "php基础");

    private String actualName;

    private String desc;

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private static final Map<String, Cost> costCache = new HashMap<>();
    private static final Map<String, LogEnum> logCache = new HashMap<>();

    private static final Map<String, StudyEnum> cache = new HashMap<>();

    StudyEnum(String actualName, String desc) {
        this.actualName = actualName;
        this.desc = desc;
    }

    //对象初始化时，执行下面的方法，将注解上的枚举值解析到cache中
    static {
        //将枚举属性值的name和Field映射为Map
        Map<String, Field> fieldCache = Arrays.stream(StudyEnum.class.getDeclaredFields()).
                filter(Field::isEnumConstant).
                collect(Collectors.toMap(Field::getName, Function.identity()));
        //遍历所有的枚举值
        for (StudyEnum studyEnum : StudyEnum.class.getEnumConstants()) {
            String keyName = studyEnum.name();
            //原始的cache
            cache.put(keyName, studyEnum);
            Field field = fieldCache.get(keyName);
            //不包含原始，则停止解析
            if (!field.isAnnotationPresent(LogEnum.class) || !field.isAnnotationPresent(Cost.class)) {
                continue;
            }
            //获取日志注解
            parsingLogAnno(keyName, field);
            //获取价格注解
            parsingCostAnno(keyName, field);
        }
    }


    /**
     * 获取注解的属性
     */
    public static StudyEnumInfo resolve(String name) {
        StudyEnum studyEnum = cache.get(name);
        if (studyEnum == null) {
            return null;
        }
        LogEnum logEnum = logCache.get(name);
        Cost cost = costCache.get(name);
        StudyEnumInfo studyEnumInfo = new StudyEnumInfo();
        studyEnumInfo.setName(studyEnumInfo.name);
        studyEnumInfo.setDesc(studyEnum.desc);
        studyEnumInfo.setLogEnumAnno(logEnum);
        studyEnumInfo.setCostAnno(cost);
        return studyEnumInfo;
    }

    /**
     * 获取注解的属性
     */
    public StudyEnumInfo resolve() {
        return resolve(this.name());
    }


    //解析日志注解
    private static void parsingLogAnno(String keyName, Field field) {
        LogEnum logEnumAnno = field.getDeclaredAnnotation(LogEnum.class);
        if (logEnumAnno != null) {
            logCache.put(keyName, logEnumAnno);
        }
    }

    //解析花费注解
    private static void parsingCostAnno(String keyName, Field field) {
        Cost costAnno = field.getDeclaredAnnotation(Cost.class);
        if (costAnno != null) {
            costCache.put(keyName, costAnno);
        }
    }


    @Data
    public static class StudyEnumInfo {
        private String name;

        private String desc;

        private LogEnum logEnumAnno;

        private Cost costAnno;
    }
}
