package cn.toutatis.xvoid.spring.support.core.db.sqlite.entities;

import cn.toutatis.xvoid.common.annotations.database.DDLField;
import cn.toutatis.xvoid.common.annotations.database.DDLTable;
import lombok.Data;

/**
 * SQLite实体类
 * @author Toutatis_Gc
 */
@Data
@DDLTable(SqliteVoidDict.TABLE)
public class SqliteVoidDict {

    public static final String TABLE = "VOID_DICT";

    public static final String AES_SECRET_KEY = "AES_SECRET";

    @DDLField(name = "KEY")
    private String key;

    @DDLField(name = "VALUE")
    private String value;

    @DDLField(name = "REMARK")
    private String remark;

    @DDLField(name = "MCH_ID")
    private String mchId;

}