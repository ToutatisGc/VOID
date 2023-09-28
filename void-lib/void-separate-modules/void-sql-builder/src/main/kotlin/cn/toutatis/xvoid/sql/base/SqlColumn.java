package cn.toutatis.xvoid.sql.base;

/**
 * 列信息
 * @author Toutatis_Gc
 */
public class SqlColumn {

    private String columnName;

    private String alias;

    private ColumnType columnType;

    private Object value;

    public SqlColumn() { }

    public SqlColumn(String columnName) { this.columnName = columnName; }

    public SqlColumn(String columnName, Object value, boolean isAlias) {
        this.columnName = columnName;
        if (isAlias){
            this.alias = String.valueOf(value);
        }else {
            this.value = value;
        }
    }


    public SqlColumn(String columnName, String alias) {
        this(columnName,alias,ColumnType.SIMPLE);
    }

    public SqlColumn(String columnName, ColumnType columnType) {
        this.columnName = columnName;
        this.columnType = columnType;
    }

    public SqlColumn(String columnName, String alias, ColumnType columnType) {
        this.columnName = columnName;
        this.alias = alias;
        this.columnType = columnType;
    }

    public enum ColumnType{

        /**
         * 对于查询则为
         * SELECT [COLUMN]
         */
        SIMPLE,

        /**
         * 条件则后置
         */
        CONDITION;

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

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }
}
