package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.standard.StringPool;
import cn.toutatis.xvoid.sql.base.AbstractBaseSqlBuilder;
import cn.toutatis.xvoid.sql.base.SQLPart;
import cn.toutatis.xvoid.sql.base.SQLType;
import cn.toutatis.xvoid.sql.base.SQLColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Toutatis_Gc
 */
public class DQLMetaBuilder<T> extends AbstractBaseSqlBuilder<T> {

    private List<SQLColumn> columns = new ArrayList<>();

    private List<SQLColumn> conditions = new ArrayList<>();

    public DQLMetaBuilder(SQLType sqlType, Class<T> entityClass){
        this.setInitial(sqlType,entityClass);
    }

    public void putColumn(SQLColumn column){
        Optional<SQLColumn> optionalSqlColumn = Optional.ofNullable(column);
        optionalSqlColumn.ifPresent(sqlColumn -> {
            if (sqlColumn.getColumnType() == SQLPart.PartType.SIMPLE){
                this.columns.add(column);
            }else if (sqlColumn.getColumnType() == SQLPart.PartType.CONDITION){
                Object value = column.getValue();
                if (value instanceof String){
                    column.setValue(StringPool.SINGLE_QUOTE+value+StringPool.SINGLE_QUOTE);
                }
                this.conditions.add(column);
            }
        });
    }

    public List<SQLColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<SQLColumn> columns) {
        this.columns = columns;
    }

    public List<SQLColumn> getConditions() {
        return conditions;
    }

    public void setConditions(List<SQLColumn> conditions) {
        this.conditions = conditions;
    }
}
