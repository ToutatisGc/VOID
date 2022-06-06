package cn.toutatis.spring;

import cn.toutatis.common.annotations.database.AutoCreateDataObject;

@AutoCreateDataObject
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
