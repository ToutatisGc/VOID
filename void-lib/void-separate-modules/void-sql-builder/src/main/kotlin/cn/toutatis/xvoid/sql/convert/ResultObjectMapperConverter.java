package cn.toutatis.xvoid.sql.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Map;

/**
 * 结果转换器
 * @author Toutatis_Gc
 */
public class ResultObjectMapperConverter<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResultObjectMapperConverter() {
        objectMapper.registerModule(new FieldMapModule());
    }

    public T convert(Map<String,Object> map,Class<T> entityClass){
        return objectMapper.convertValue(map,entityClass);
    }

}
