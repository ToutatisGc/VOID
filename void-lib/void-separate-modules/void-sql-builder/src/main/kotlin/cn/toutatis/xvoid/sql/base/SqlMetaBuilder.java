package cn.toutatis.xvoid.sql.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toutatis_Gc
 */
public class SqlMetaBuilder extends AbstractBaseSqlBuilder {

    private List<SqlColumn> columns = new ArrayList<>();

    public SqlMetaBuilder(SQLType sqlType){
        this.setSqlTypeInitial(sqlType);
    }

    public void putColumn(SqlColumn column){
        this.columns.add(column);
    }

}
