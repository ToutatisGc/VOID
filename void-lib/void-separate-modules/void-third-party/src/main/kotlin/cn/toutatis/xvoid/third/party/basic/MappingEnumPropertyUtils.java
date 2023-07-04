package cn.toutatis.xvoid.third.party.basic;

import cn.toutatis.xvoid.third.party.aMap.AMapAPI;
import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.third.party.openai.Cost;
import cn.toutatis.xvoid.third.party.openai.StudyEnum;
import cn.toutatis.xvoid.toolkit.log.LogEnum;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Toutatis_Gc
 */
public class MappingEnumPropertyUtils {

    private static final Map<String, Enum> cache = new HashMap<String, Enum>();
    private static final Map<String, LogEnum> LOG_RESOLVER = new HashMap<>();
    private static final Map<String, APIDocument> DOCUMENT_RESOLVER = new HashMap<>();
    
    public static <T extends Enum<T>> void mappingProperties(Class<T> clazz) {
        LOG_RESOLVER.clear();
        //将枚举属性值的name和Field映射为Map
        Map<String, Field> fieldCache = Arrays.stream(clazz.getDeclaredFields())
                .filter(Field::isEnumConstant)
                .collect(Collectors.toMap(Field::getName, Function.identity()));
        //遍历所有的枚举值
        for (T t : clazz.getEnumConstants()) {
            String keyName = t.name();
            //原始的cache
            cache.put(keyName, t);
            Field field = fieldCache.get(keyName);
            //不包含原始，则停止解析
            if (!field.isAnnotationPresent(LogEnum.class) || !field.isAnnotationPresent(APIDocument.class)) {
                continue;
            }
            //获取日志注解
            parsingLogAnnotation(keyName, field);
            //获取价格注解
            parsingDocumentAnnotation(keyName, field);
        }
    }

    /**
     *
     * @param keyName
     * @param field
     */
    private static void parsingLogAnnotation(String keyName, Field field) {
        LogEnum logEnumAnnotation = field.getDeclaredAnnotation(LogEnum.class);
        if (logEnumAnnotation != null) {
            LOG_RESOLVER.put(keyName, logEnumAnnotation);
        }
    }

    private static void parsingDocumentAnnotation(String keyName, Field field) {
        APIDocument apiDocument = field.getDeclaredAnnotation(APIDocument.class);
        if (apiDocument != null) {
            DOCUMENT_RESOLVER.put(keyName, apiDocument);
        }
    }

    /**
     * 获取注解的属性
     */
    public static ApiDocumentInfo resolve(String name) {
        Enum anEnum = cache.get(name);
        if (anEnum == null) {
            return null;
        }
        LogEnum logEnum = LOG_RESOLVER.get(name);
        APIDocument apiDocument = DOCUMENT_RESOLVER.get(name);
        ApiDocumentInfo apiDocumentInfo = new ApiDocumentInfo();
        apiDocumentInfo.setName(anEnum.name());
        apiDocumentInfo.setDescription(apiDocument.description());
        apiDocumentInfo.setLogEnum(logEnum);
        apiDocumentInfo.setApiDocument(apiDocument);
        return apiDocumentInfo;
    }

    
}
