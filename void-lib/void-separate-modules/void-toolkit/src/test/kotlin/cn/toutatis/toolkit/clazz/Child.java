package cn.toutatis.toolkit.clazz;

public class Child extends Parent {

    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Child{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
