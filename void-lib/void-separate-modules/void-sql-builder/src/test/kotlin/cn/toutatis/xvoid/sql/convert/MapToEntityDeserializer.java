package cn.toutatis.xvoid.sql.convert;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;
import java.util.Map;

public class MapToEntityDeserializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (type instanceof Class && Map.class.isAssignableFrom((Class<?>) type)) {
            Map<String, Object> map = parser.parseObject();
            // 在这里执行自定义转换逻辑，将map转为你的Java实体类对象
            // 这里只是一个简单示例，你需要根据你的实际需求进行适当的转换
            // 以下是一个伪代码示例，用于说明思路
//            YourEntityClass entity = new YourEntityClass();
//            entity.setField1((String) map.get("field1"));
//            entity.setField2((Integer) map.get("field2"));
//            // 更多字段的转换...
//            return (T) entity;
            return null;
        }
        return null;
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
