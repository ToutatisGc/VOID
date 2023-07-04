package cn.toutatis.xvoid.third.party.basic;

import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import cn.toutatis.xvoid.toolkit.validator.Validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 文档注解映射工具
 * @author Toutatis_Gc
 */
public class DocumentMappingEnumPropertyUtils {

    private static final Map<String, Enum> FIELD_CACHE = new HashMap<String, Enum>();
    private static final Map<String, LogEnum> LOG_RESOLVER = new HashMap<>();
    private static final Map<String, APIDocument> DOCUMENT_RESOLVER = new HashMap<>();

    /**
     * 捕获满足条件的字段映射到集合
     * @param clazz API类
     * @param <T> API泛型
     */
    public static <T extends Enum<T>> void mappingProperties(Class<T> clazz) {
        APIDocument declaredAnnotation = clazz.getDeclaredAnnotation(APIDocument.class);
//        FIELD_CACHE.clear();
//        LOG_RESOLVER.clear();
//        DOCUMENT_RESOLVER.clear();

        Map<String, Field> fieldCache = Arrays.stream(clazz.getDeclaredFields())
                .filter(Field::isEnumConstant)
                .collect(Collectors.toMap(Field::getName, Function.identity()));

        for (T t : clazz.getEnumConstants()) {
            String keyName = t.name();
            FIELD_CACHE.put(keyName, t);
            Field field = fieldCache.get(keyName);
            if (!field.isAnnotationPresent(LogEnum.class) || !field.isAnnotationPresent(APIDocument.class)) {
                continue;
            }
            parsingLogAnnotation(keyName, field);
            parsingDocumentAnnotation(keyName, field);
        }
    }

    /**
     * 解析文本
     * @param name 类名
     * @return 文档信息
     */
    public static ApiDocumentInfo resolve(String name) {
        Enum anEnum = FIELD_CACHE.get(name);
        if (anEnum == null) { return null; }
        ApiDocumentInfo apiDocumentInfo = new ApiDocumentInfo();
        Class<? extends Enum> aClass = anEnum.getClass();
        apiDocumentInfo.setApiClass(aClass);
        APIDocument declaredAnnotation = aClass.getDeclaredAnnotation(APIDocument.class);
        if (declaredAnnotation != null){
            apiDocumentInfo.setApiSetDocument(declaredAnnotation);
            apiDocumentInfo.setApiSetName(declaredAnnotation.name());
            apiDocumentInfo.setApiSetDescription(declaredAnnotation.description());
            apiDocumentInfo.setApiSetUrl(declaredAnnotation.url());
            apiDocumentInfo.setApiSetVersion(declaredAnnotation.version());
        }else {
            apiDocumentInfo.setApiSetName("未知");
            apiDocumentInfo.setApiSetDescription("未知");
            apiDocumentInfo.setApiSetUrl("未知");
            apiDocumentInfo.setApiSetVersion("未知");
        }
        LogEnum logEnum = LOG_RESOLVER.get(name);
        APIDocument apiDocument = DOCUMENT_RESOLVER.get(name);
        BaseAPI api = (BaseAPI) anEnum;
        apiDocumentInfo.setMethodName(Validator.strIsBlank(apiDocument.name())? api.getMethodName() : apiDocument.name());
        apiDocumentInfo.setMethodDescription(apiDocument.description());
        apiDocumentInfo.setMethodVersion(apiDocument.version());
        apiDocumentInfo.setRequestMethod(((BaseAPI) anEnum).getMethod().name());
        apiDocumentInfo.setMethodUrl(api.getUrl());
        ArgumentsSchema argumentsSchema = api.getArgumentsSchema();
        apiDocumentInfo.setArgumentsSchema(argumentsSchema);
        apiDocumentInfo.setLogEnum(logEnum);
        apiDocumentInfo.setApiDocument(apiDocument);
        return apiDocumentInfo;
    }

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
    
}
