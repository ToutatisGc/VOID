package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@DDLTable("test_own")
@Getter() @Setter
public class TestTableOwn {

    private String name;

    private Integer age;


}
