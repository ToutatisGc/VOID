package cn.toutatis.xvoid.sql.base;

public class SQLPart {

    private String key;

    private String value;

    private String order;

    public enum PartType {

        /**
         * 对于查询则为
         * SELECT [COLUMN]
         */
        SIMPLE,

        /**
         * 子查询
         */
        CHILD,

        /**
         * 逻辑符
         */
        LOGICAL,

        /**
         * 条件则后置
         */
        CONDITION;

    }
}
