package cn.toutatis.xvoid.sql.convert;

import cn.toutatis.xvoid.common.annotations.database.AssignField;
import cn.toutatis.xvoid.toolkit.clazz.ReflectToolkit;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 结果转换器
 * @author Toutatis_Gc
 */
public class ResultObjectMapperConverter {

    private static volatile ResultObjectMapperConverter INSTANCE;

    private ResultObjectMapperConverter(){}

    public static ResultObjectMapperConverter instance(){
        if (INSTANCE == null){
            synchronized (ResultObjectMapperConverter.class){
                if (INSTANCE == null){
                    INSTANCE = new ResultObjectMapperConverter();
                }
            }
        }
        return INSTANCE;
    }

    public <T> T convert(Map<String,Object> map,Class<T> entityClass) throws Exception {
        return ReflectToolkit.convertMapToEntity(map,entityClass);
    }

}
