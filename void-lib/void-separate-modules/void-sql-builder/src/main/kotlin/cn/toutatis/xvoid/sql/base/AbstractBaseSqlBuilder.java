package cn.toutatis.xvoid.sql.base;


import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL 必要信息
 * @author Toutatis_Gc
 */
public abstract class AbstractBaseSqlBuilder<T> implements BaseSqlBuilder<T> {

    /**
     * SQL类型
     */
    private SQLType sqlType;

    /**
     * 实体类
     */
    private Class<T> entityClass;

    /**
     * 表名
     */

    private String table;
    /**
     * 查询条件
     */
    private List<SQLCondition> conditions = new ArrayList<>();

    private boolean showSql = false;

    @Override
    public void setInitial(SQLType initial,Class<T> entityClass) {
        if (entityClass == null) {
            throw new NullPointerException("实体类不得为空");
        }
        this.sqlType = initial;
        this.entityClass = entityClass;
        this.setTable(entityClass);
    }

    public void setInitial(SQLType initial,String table) {
        this.sqlType = initial;
        this.setTable(table);
    }

    public SQLType getSqlType() {
        return sqlType;
    }

    public String getTable() {
        return table;
    }

    /**
     * 设置表名
     * @param tableClass 查询表实体
     */
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

    protected void setTable(String tableName){
        this.table = tableName;
    }

    public void putCondition(SQLCondition condition){
        this.conditions.add(condition);
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<SQLCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<SQLCondition> conditions) {
        this.conditions = conditions;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }
}
