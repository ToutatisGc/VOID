package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.annotations.database.DDLTable;

@DDLTable("test_own")
public class TestTableOwn {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
