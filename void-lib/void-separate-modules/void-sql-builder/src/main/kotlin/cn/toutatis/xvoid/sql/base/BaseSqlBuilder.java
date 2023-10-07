package cn.toutatis.xvoid.sql.base;

/**
 * @author Toutatis_Gc
 */
public interface BaseSqlBuilder<T> {

    /**
     * SELECT / INSERT / UPDATE
     * @param initial 初始语句
     */
    void setInitial(SQLType initial, Class<T> entityClass);
}
