package cn.toutatis.xvoid.sql.base;


import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Table;

/**
 * SQL 必要信息
 * @author Toutatis_Gc
 */
public abstract class AbstractBaseSqlBuilder<T> implements BaseSqlBuilder<T> {

    private SQLType sqlType;

    private Class<T> entityClass;

    private String table;

    @Override
    public void setInitial(SQLType initial,Class<T> entityClass) {
        if (entityClass == null) {
            throw new NullPointerException("实体类不得为空");
        }
        this.sqlType = initial;
        this.entityClass = entityClass;
        this.setTable(entityClass);
    }

    public SQLType getSqlType() {
        return sqlType;
    }

    public String getTable() {
        return table;
    }

    protected void setTable(Class<T> tableClass) {
        DDLTable ddlTable = tableClass.getDeclaredAnnotation(DDLTable.class);
        if (ddlTable != null) {
            this.table = ddlTable.value();
            return;
        }
        TableName tableName = tableClass.getDeclaredAnnotation(TableName.class);
        if (tableName != null) {
            this.table = tableName.value();
            return;
        }
        Table table = tableClass.getDeclaredAnnotation(Table.class);
        if (table != null) {
            this.table = table.name();
            return;
        }
        String name = tableClass.getSimpleName();
        this.table = name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}
