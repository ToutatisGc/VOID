package cn.toutatis.spring;

import cn.toutatis.common.annotations.database.AutoCreateDataObject;
import lombok.Data;

/**
 * @author Administrator
 */
@AutoCreateDataObject
@Data
public class Person {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
