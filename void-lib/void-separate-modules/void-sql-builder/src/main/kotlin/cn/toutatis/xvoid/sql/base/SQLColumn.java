package cn.toutatis.xvoid.sql.base;

/**
 * 列信息
 * @author Toutatis_Gc
 */
public class SQLColumn extends SQLPart {

    private String columnName;

    private String alias;

    private PartType partType;

    private Object value;

    public SQLColumn() { }

    public SQLColumn(String columnName) { this.columnName = columnName; }

    public SQLColumn(String columnName, Object value, boolean isAlias) {
        this.columnName = columnName;
        if (isAlias){
            this.alias = String.valueOf(value);
        }else {
            this.value = value;
        }
    }


    public SQLColumn(String columnName, String alias) {
        this(columnName,alias, PartType.SIMPLE);
    }

    public SQLColumn(String columnName, PartType partType) {
        this.columnName = columnName;
        this.partType = partType;
    }

    public SQLColumn(String columnName, Object value, PartType partType) {
        this.columnName = columnName;
        this.value = value;
        this.partType = partType;
    }

    public SQLColumn(String columnName, String alias, PartType partType) {
        this.columnName = columnName;
        this.alias = alias;
        this.partType = partType;
    }


    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
