package cn.toutatis.xvoid.sql.base;

import cn.toutatis.xvoid.sql.dql.DQLBuilder;
import cn.toutatis.xvoid.sql.dql.DQLMetaBuilder;

/**
 * 列信息
 * @author Toutatis_Gc
 */
public class SQLColumn extends SQLPart {

    private String alias;

    private DQLBuilder<?> childQuery;

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

    public SQLColumn(DQLBuilder<?> field,String alias) {
        DQLMetaBuilder<?> childMeta = field.getMetaInfo();
        if (childMeta.getColumns().size() != 1){
            throw new IllegalArgumentException("子查询只能查询一个字段");
        }
        this.childQuery = field;
        this.alias = alias;
        this.partType = PartType.CHILD;
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

    public DQLBuilder<?> getChildQuery() {
        return childQuery;
    }

    public void setChildQuery(DQLBuilder<?> childQuery) {
        this.childQuery = childQuery;
    }

    public PartType getPartType() {
        return partType;
    }

    public void setPartType(PartType partType) {
        this.partType = partType;
    }
}
