package cn.toutatis.xvoid.project.spring;

import cn.toutatis.xvoid.common.annotations.database.AssignField;

public class VoidSpringContextVariables {

    /**
     * AES加密密钥
     */
    public static final String AES_SECRET_KEY = "AES_SECRET";

    /**
     * 系统过期时间
     */
    public static final String SYSTEM_EXPIRED_KEY = "VOID_EXPIRED";

    /**
     * Db Already Exec Init
     */
    public static final String DB_ALREADY_EXEC_INIT_KEY = "DB_ALREADY_EXEC_INIT";
    
    @AssignField(name = AES_SECRET_KEY)
    private String aesSecret;

    @AssignField(name = SYSTEM_EXPIRED_KEY)
    private String systemExpired;

    @AssignField(name = DB_ALREADY_EXEC_INIT_KEY)
    private String dbAlreadyInit;

    public String getAesSecret() {
        return aesSecret;
    }

    public void setAesSecret(String aesSecret) {
        this.aesSecret = aesSecret;
    }

    public String getSystemExpired() {
        return systemExpired;
    }

    public void setSystemExpired(String systemExpired) {
        this.systemExpired = systemExpired;
    }

    public String getDbAlreadyInit() {
        return dbAlreadyInit;
    }

    public void setDbAlreadyInit(String dbAlreadyInit) {
        this.dbAlreadyInit = dbAlreadyInit;
    }
}
