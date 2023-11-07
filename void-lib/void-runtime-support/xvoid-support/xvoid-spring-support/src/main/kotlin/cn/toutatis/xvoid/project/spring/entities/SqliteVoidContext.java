package cn.toutatis.xvoid.project.spring.entities;

import cn.toutatis.xvoid.common.annotations.database.AssignField;
import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import lombok.Data;

/**
 * SQLite实体类
 * @author Toutatis_Gc
 */
@Data
@DDLTable(SqliteVoidContext.TABLE)
public class SqliteVoidContext {

    /**
     * 环境上下文表名
     */
    public static final String TABLE = "VOID_CONTEXT";

    /**
     * 数据库初始化
     */
    public static final String DB_ALREADY_EXEC_INIT = "DB_ALREADY_EXEC_INIT";

    public static final String KEY_FIELD = "KEY";

    public static final String VALUE_FIELD = "VALUE";

    @AssignField(name = KEY_FIELD)
    private String key;

    @AssignField(name = VALUE_FIELD)
    private String value;

    @AssignField(name = "REMARK")
    private String remark;

    @AssignField(name = "MCH_ID")
    private String mchId;

}
