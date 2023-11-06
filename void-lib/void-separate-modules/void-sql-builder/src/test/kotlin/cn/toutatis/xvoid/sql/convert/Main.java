package cn.toutatis.xvoid.sql.convert;

import cn.toutatis.xvoid.sql.dql.TestTableJava;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Map<String, Object> info = Map.of("NAME", "foo");
        // 创建一个配置对象
        ParserConfig parserConfig = new ParserConfig();
        // 将自定义反序列化器注册到配置对象中
        parserConfig.putDeserializer(Object.class, new MapToEntityDeserializer());
        // 使用自定义配置进行反序列化
        TestTableJava entity = JSON.parseObject(JSON.toJSONString(info), TestTableJava.class, parserConfig);


//        ObjectMapper objectMapper = new ObjectMapper();
//
//        SimpleModule customModule = new SimpleModule();
//        customModule.addDeserializer(Object.class, new CustomFieldDeserializer());
//        objectMapper.registerModule(customModule);
//
//        String json = "{\"customField\": \"customValue\"}";
//
//        MyEntity entity = objectMapper.readValue(json, MyEntity.class);
//
//        System.out.println("Custom Field: " + entity.getCustomField());
    }
}
