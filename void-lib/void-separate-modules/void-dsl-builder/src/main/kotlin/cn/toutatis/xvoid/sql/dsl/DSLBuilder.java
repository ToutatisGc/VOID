package cn.toutatis.xvoid.sql.dsl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class DSLBuilder<T> {

    private Class<T> entityClass;

    private Map<String,Object> sqlInfo;


    public void eq(){

    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}