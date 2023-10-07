package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.sql.base.*;
import cn.toutatis.xvoid.toolkit.validator.Validator;

import java.util.*;

/**
 * @author Toutatis_Gc
 */
public class DQLMetaBuilder<T> extends AbstractBaseSqlBuilder<T> {

    /**
     * 查询列
     */
    private Map<String,SQLColumn> columns = new LinkedHashMap<>();

    public DQLMetaBuilder(SQLType sqlType, Class<T> entityClass){
        this.setInitial(sqlType,entityClass);
    }

    public void putColumn(SQLColumn column){
        Optional<SQLColumn> optionalSqlColumn = Optional.ofNullable(column);
        optionalSqlColumn.ifPresent(sqlColumn -> {
            String columnName = column.getField();
            if (Validator.strIsBlank(columnName)){
                throw new NullPointerException("字段名不得为NULL");
            }
            String alias = sqlColumn.getAlias();
            if (Validator.strNotBlank(alias)){
                columnName = alias;
            }
            if (sqlColumn.getColumnType() == SQLColumn.PartType.SIMPLE){
                this.columns.putIfAbsent(columnName,column);
            }else if (sqlColumn.getColumnType() == SQLColumn.PartType.CHILD){
                // TODO 子查询
            }
        });
    }

    public Map<String, SQLColumn> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, SQLColumn> columns) {
        this.columns = columns;
    }

}
