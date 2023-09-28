package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.sql.base.*;

public class DQLBuilder<T> {

    private Class<T> entityClass;

    private final SqlMetaBuilder metaInfo = new SqlMetaBuilder(SQLType.SELECT);

    public DQLBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public DQLBuilder<T> eq(String column, Object value){
        // TODO 看用哪种形式存储数据
//        metaInfo.putColumn(new SqlColumn(column,value, SqlColumn.ColumnType.CONDITION));
        return this;
    }

    public DQLBuilder<T> eq(XFunc<T,?> column, Object value) throws Exception {
        this.eq(LambdaHandler.getFieldName(LambdaHandler.serialize(column)), value);
        return this;
    }

    public String build(){
        StringBuilder sql = new StringBuilder(metaInfo.getSqlType().toString());

        return sql.toString();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}