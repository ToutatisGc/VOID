package cn.toutatis.xvoid.sql.dql;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test_mp")
public class TestTableMp {

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
