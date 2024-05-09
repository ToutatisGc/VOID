package cn.toutatis.toolkit.clazz;

import lombok.Data;

@Data
public class TestEntity {

    private String name;

    private int age;


    private String getTestName(){
        return null;
    }

    public String getName() {
        System.err.println("调用Getter");
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        System.err.println("调用Setter");
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
