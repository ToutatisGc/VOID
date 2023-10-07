package cn.toutatis.xvoid.sql.base;

public enum SQLPlaceHolder {

    /**
     * SELECT 语句查询字段
     */
    SELECT_COLUMNS("<COLUMNS>"),

    /**
     * 去重关键字
     */
    DISTINCT("DISTINCT");

    private final String value;

    SQLPlaceHolder(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
