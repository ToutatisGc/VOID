package cn.toutatis.xvoid.sql.base;

/**
 * 列信息
 * @author Toutatis_Gc
 */
public class SQLColumn extends SQLPart {

    private String alias;

    private PartType partType;

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

    }

    public SQLColumn() { }

    public SQLColumn(String field) {
        this(field, PartType.SIMPLE);
    }


    public SQLColumn(String field, String alias) {
        this(field,alias, PartType.SIMPLE);
    }

    public SQLColumn(String field, PartType partType) {
        setField(field);
        this.partType = partType;
    }

    public SQLColumn(String field, String alias, PartType partType) {
        setField(field);
        this.alias = alias;
        this.partType = partType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public PartType getColumnType() {
        return partType;
    }

    public void setColumnType(PartType partType) {
        this.partType = partType;
    }

}
