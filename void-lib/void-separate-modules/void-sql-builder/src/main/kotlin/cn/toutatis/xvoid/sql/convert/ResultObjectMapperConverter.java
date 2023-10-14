package cn.toutatis.xvoid.sql.convert;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * 结果转换器
 * @author Toutatis_Gc
 */
public class ResultObjectMapperConverter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static volatile ResultObjectMapperConverter INSTANCE;

    private ResultObjectMapperConverter(){}

    public static ResultObjectMapperConverter instance(){
        if (INSTANCE == null){
            synchronized (ResultObjectMapperConverter.class){
                if (INSTANCE == null){
                    INSTANCE = new ResultObjectMapperConverter();
                    OBJECT_MAPPER.registerModule(new FieldMapModule());
                }
            }
        }
        return INSTANCE;
    }

    public <T> T convert(Map<String,Object> map,Class<T> entityClass){
        return OBJECT_MAPPER.convertValue(map,entityClass);
    }

}
