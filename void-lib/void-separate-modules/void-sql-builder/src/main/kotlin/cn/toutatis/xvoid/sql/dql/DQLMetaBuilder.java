package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.standard.StringPool;
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

    protected DQLMetaBuilder(SQLType sqlType, Class<T> entityClass){
        this.setInitial(sqlType,entityClass);
    }

    protected DQLMetaBuilder(SQLType sqlType, String table){
        this.setInitial(sqlType,table);
    }

    public void putColumn(SQLColumn column){
        Optional<SQLColumn> optionalSqlColumn = Optional.ofNullable(column);
        optionalSqlColumn.ifPresent(sqlColumn -> {
            String columnName = column.getField();
            if (Validator.strIsBlank(columnName) && column.getPartType() != SQLColumn.PartType.CHILD){
                throw new NullPointerException("字段名不得为NULL");
            }
            String alias = sqlColumn.getAlias();
            if (Validator.strNotBlank(alias)){
                columnName = alias;
            }
            if (sqlColumn.getColumnType() == SQLColumn.PartType.SIMPLE){
                this.columns.putIfAbsent(columnName,column);
            }else if (sqlColumn.getColumnType() == SQLColumn.PartType.CHILD){
                String build = sqlColumn.getChildQuery().build();
                sqlColumn.setField(StringPool.LEFT_BRACKET+build+StringPool.RIGHT_BRACKET);
                this.columns.putIfAbsent(alias,column);
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
