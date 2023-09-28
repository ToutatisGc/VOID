package cn.toutatis.xvoid.sql.base;

/**
 *
 * @author Toutatis_Gc
 */
public class AbstractBaseSqlBuilder implements BaseSqlBuilder {

    private SQLType sqlType;

    @Override
    public void setSqlTypeInitial(SQLType initial) {
        this.sqlType = initial;
    }

    public SQLType getSqlType() {
        return sqlType;
    }
}
