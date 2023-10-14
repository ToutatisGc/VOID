package cn.toutatis.xvoid.sql.convert;

import cn.toutatis.xvoid.common.annotations.database.DDLField;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @author Toutatis_Gc
 */
public class FieldMapModule extends SimpleModule {
    public FieldMapModule() {
        addDeserializer(Object.class, new MapToEntityDeserializer());
        addSerializer(Object.class,new MapToEntitySerializer());
    }

    private static class MapToEntityDeserializer extends JsonDeserializer<Object> {
        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ObjectNode node = p.getCodec().readTree(p);
            Class<?> entityClass = ctxt.getContextualType().getRawClass();
            try {
                Object entity = entityClass.getDeclaredConstructor().newInstance();
                for (Field field : entityClass.getDeclaredFields()) {
                    if (field.isAnnotationPresent(DDLField.class)) {
                        DDLField annotation = field.getAnnotation(DDLField.class);
                        if (annotation != null){
                            String mapKey = annotation.name();
                            if (node.has(mapKey)) {
                                field.setAccessible(true);
                                field.set(entity, p.getCodec().treeToValue(node.get(mapKey), field.getType()));
                            }
                        }else {

                        }
                    }
                }
                return entity;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }
    }

    private static class MapToEntitySerializer extends JsonSerializer<Object> {

        @Override
        public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            System.err.println(o);
        }
    }
}