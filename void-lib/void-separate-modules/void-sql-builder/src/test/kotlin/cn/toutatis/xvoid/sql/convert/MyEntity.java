package cn.toutatis.xvoid.sql.convert;

public class MyEntity {
    @CustomField
    private String customField;

    public String getCustomField() {
        return customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    // 其他字段和方法
}
