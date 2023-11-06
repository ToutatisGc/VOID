package cn.toutatis.xvoid.sql.dql;

import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@DDLTable("test_own")
@Getter() @Setter
@ToString
public class TestTableOwn {

    private String name;

    private Integer age;

    private TestTableJava inner;


}
