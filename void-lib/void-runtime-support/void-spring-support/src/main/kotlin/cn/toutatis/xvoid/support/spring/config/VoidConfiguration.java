package cn.toutatis.xvoid.support.spring.config;

import cn.toutatis.xvoid.Version;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Toutatis_Gc
 * VOID环境下SpringBoot环境变量
 */
@ConfigurationProperties("void")
public class VoidConfiguration {

    /**
     * 是否开启调试模式
     */
    private RunMode mode = RunMode.DEV;

    /**
     * 版本号
     */
    private Version version = Version.$DEFAULT;

    /**
     * 是否开启平台多用户模式
     */
    private Boolean platformMode = false;

    /**
     * 是否开启展示模式，展示模式下所有数据不得操作
     */
    private Boolean showMode = false;

    private VoidConfiguration.GlobalLogConfig globalLogConfig = new GlobalLogConfig();
    private VoidConfiguration.DocConfig docConfig = new DocConfig();
    private VoidConfiguration.GlobalOrmConfig globalOrmConfig = new GlobalOrmConfig();
    private VoidConfiguration.GlobalServiceConfig globalServiceConfig = new GlobalServiceConfig();

    /**
     * 全局日志环境变量
     */
    public static class GlobalLogConfig{

        /**
         * 记录每次请求的方法
         */
        private Boolean recordRequest = true;

        /**
         * 记录静态文件访问
         */
        private Boolean recordStaticResource = true;

        /**
         * 请求携带RID
         */
        private Boolean recordRequestId = true;

        /**
         * 记录请求参数到日志文件
         */
        private Boolean recordRequestParams = true;

        /**
         * 记录请求到数据库
         */
        private Boolean recordToDb = true;

        public Boolean getRecordRequest() {
            return recordRequest;
        }

        public void setRecordRequest(Boolean recordRequest) {
            this.recordRequest = recordRequest;
        }

        public Boolean getRecordStaticResource() {
            return recordStaticResource;
        }

        public void setRecordStaticResource(Boolean recordStaticResource) {
            this.recordStaticResource = recordStaticResource;
        }

        public Boolean getRecordRequestId() {
            return recordRequestId;
        }

        public void setRecordRequestId(Boolean recordRequestId) {
            this.recordRequestId = recordRequestId;
        }

        public Boolean getRecordRequestParams() {
            return recordRequestParams;
        }

        public void setRecordRequestParams(Boolean recordRequestParams) {
            this.recordRequestParams = recordRequestParams;
        }

        public Boolean getRecordToDb() {
            return recordToDb;
        }

        public void setRecordToDb(Boolean recordToDb) {
            this.recordToDb = recordToDb;
        }
    }

    /**
     * swagger文档配置
     */
    public static class DocConfig{

        /**
         * 扫描包
         */
        private String basePackage = "cn.toutatis.xvoid";

        /**
         * 文档描述
         */
        private String description = "VOID RESTFUL APIS";

        /**
         * 文档标题
         */
        private String title = "VOID文档";

        public String getBasePackage() {
            return basePackage;
        }

        public void setBasePackage(String basePackage) {
            this.basePackage = basePackage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    /**
     * ORM框架配置
     */
    public static class GlobalOrmConfig{

        /**
         * 使用表前缀
         */
        private Boolean useTablePrefix = false;

        /**
         * 表前缀名称
         */
        private String tablePrefix = "";

        public Boolean getUseTablePrefix() {
            return useTablePrefix;
        }

        public void setUseTablePrefix(Boolean useTablePrefix) {
            this.useTablePrefix = useTablePrefix;
        }

        public String getTablePrefix() {
            return tablePrefix;
        }

        public void setTablePrefix(String tablePrefix) {
            this.tablePrefix = tablePrefix;
        }
    }

    /**
     * 全局服务配置
     */
    public static class GlobalServiceConfig{

        /**
         * 自动填充请求code
         */
        private Boolean autoConfig = true;

        /**
         * 使用详细信息模式
         */
        private Boolean useDetailedMode = true;

        public Boolean getAutoConfig() {
            return autoConfig;
        }

        public void setAutoConfig(Boolean autoConfig) {
            this.autoConfig = autoConfig;
        }

        public Boolean getUseDetailedMode() {
            return useDetailedMode;
        }

        public void setUseDetailedMode(Boolean useDetailedMode) {
            this.useDetailedMode = useDetailedMode;
        }
    }

    public GlobalServiceConfig getGlobalServiceConfig() {
        return globalServiceConfig;
    }

    public void setGlobalServiceConfig(GlobalServiceConfig globalServiceConfig) {
        this.globalServiceConfig = globalServiceConfig;
    }

    public GlobalLogConfig getGlobalLogConfig() {
        return globalLogConfig;
    }

    public void setGlobalLogConfig(GlobalLogConfig globalLogConfig) {
        this.globalLogConfig = globalLogConfig;
    }

    public DocConfig getDocConfig() {
        return docConfig;
    }

    public void setDocConfig(DocConfig docConfig) {
        this.docConfig = docConfig;
    }

    public GlobalOrmConfig getGlobalOrmConfig() {
        return globalOrmConfig;
    }

    public void setGlobalOrmConfig(GlobalOrmConfig globalOrmConfig) {
        this.globalOrmConfig = globalOrmConfig;
    }

    public RunMode getMode() {
        return mode;
    }

    public void setMode(RunMode mode) {
        this.mode = mode;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public Boolean getPlatformMode() {
        return platformMode;
    }

    public void setPlatformMode(Boolean platformMode) {
        this.platformMode = platformMode;
    }

    public Boolean getShowMode() {
        return showMode;
    }

    public void setShowMode(Boolean showMode) {
        this.showMode = showMode;
    }
}
