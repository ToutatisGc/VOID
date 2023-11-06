package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.annotations.database.AssignField;

public class TestTableJava {

    @AssignField(name = "NAME")
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

    @Override
    public String toString() {
        return "TestTableJava{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
