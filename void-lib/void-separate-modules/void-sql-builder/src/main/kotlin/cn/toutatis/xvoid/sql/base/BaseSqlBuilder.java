package cn.toutatis.xvoid.sql.base;

/**
 * @author Toutatis_Gc
 */
public interface BaseSqlBuilder {

    /**
     * SELECT / INSERT / UPDATE
     * @param initial 初始语句
     */
    void setSqlTypeInitial(SQLType initial);

}
