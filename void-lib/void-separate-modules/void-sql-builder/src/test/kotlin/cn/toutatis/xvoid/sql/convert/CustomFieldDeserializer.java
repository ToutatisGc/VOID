package cn.toutatis.xvoid.sql.convert;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class CustomFieldDeserializer extends JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        System.err.println(1111);
        ObjectCodec objectCodec = p.getCodec();
        ObjectNode node = objectCodec.readTree(p);

        // 在这里处理特殊逻辑，例如从 JSON 中获取指定字段的值
        JsonNode customFieldNode = node.get("customField");
        String customFieldValue = customFieldNode != null ? customFieldNode.asText() : null;

        // 创建自定义对象并返回
//        CustomObject customObject = new CustomObject();
//        customObject.setCustomField(customFieldValue);

        return null;
    }
}
