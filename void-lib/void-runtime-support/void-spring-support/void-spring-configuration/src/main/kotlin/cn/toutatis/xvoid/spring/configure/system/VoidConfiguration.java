package cn.toutatis.xvoid.spring.configure.system;

import cn.toutatis.xvoid.Version;
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode;
import cn.toutatis.xvoid.spring.configure.system.enums.qos.AntiLeechStrategy;
import cn.toutatis.xvoid.spring.configure.system.enums.storage.ObjectStorageMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

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

    private GlobalLogConfig globalLogConfig = new GlobalLogConfig();
    private DocConfig docConfig = new DocConfig();
    private GlobalOrmConfig globalOrmConfig = new GlobalOrmConfig();
    private GlobalServiceConfig globalServiceConfig = new GlobalServiceConfig();
    private MinIoConfig minIoConfig = new MinIoConfig();
    private QualityOfServiceStrategyConfig qualityOfServiceStrategyConfig = new QualityOfServiceStrategyConfig();

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

        /**
         * 文件按照MIME-TYPE分类
         */
        private Boolean fileObjectClassify = true;

        /**
         * 是否启用文件压缩
         */
        private Boolean compressFile= true;

        private ObjectStorageMode objectStorageMode = ObjectStorageMode.LOCAL;

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

        public Boolean getFileObjectClassify() { return fileObjectClassify; }

        public void setFileObjectClassify(Boolean fileObjectClassify) { this.fileObjectClassify = fileObjectClassify; }

        public Boolean getCompressFile() { return compressFile; }

        public void setCompressFile(Boolean compressFile) { this.compressFile = compressFile; }

        public ObjectStorageMode getObjectStorageMode() {
            return objectStorageMode;
        }

        public void setObjectStorageMode(ObjectStorageMode objectStorageMode) {
            this.objectStorageMode = objectStorageMode;
        }
    }

    /**
     * MinIO客户端连接参数
     */
    public static class MinIoConfig{

        /**
         * MinIO连接端点
         */
        private String endpoint =  "http://localhost:9000";

        /**
         * MinIO默认用户名[root]
         */
        private String accessKey = "root";

        /**
         * MinIO用户密码[必填]
         */
        private String accessSecret;

        /**
         * MinIO访问区域
         * 集群下需要填入地区，根据就近原则优化访问速度
         */
        private String region = "standard";

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getAccessSecret() {
            return accessSecret;
        }

        public void setAccessSecret(String accessSecret) {
            this.accessSecret = accessSecret;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

    }

    /**
     * 服务质量策略配置
     */
    public static class QualityOfServiceStrategyConfig{

        /**
         * 是否启用请求接口次数限制
         */
        private Boolean enableRequestCountLimit = false;

        /**
         * 单个接口在defaultTimeSlice时间片中最多请求次数
         */
        private Integer maxRequestCount = 30;

        /**
         * 默认时间片
         */
        private Duration defaultTimeSlice = Duration.ofMinutes(1);

        /**
         * 盗链策略
         */
        private AntiLeechStrategy leechStrategy = AntiLeechStrategy.ALLOW;

        /**
         * 允许访问链接的外部域名
         * TODO 继承接口则按照接口行为执行
         */
        private List<String> allowOuterHosts;

        public AntiLeechStrategy getLeechStrategy() {
            return leechStrategy;
        }

        public void setLeechStrategy(AntiLeechStrategy leechStrategy) {
            this.leechStrategy = leechStrategy;
        }

        public List<String> getAllowOuterHosts() {
            return allowOuterHosts;
        }

        public void setAllowOuterHosts(List<String> allowOuterHosts) {
            this.allowOuterHosts = allowOuterHosts;
        }

        public Boolean getEnableRequestCountLimit() {
            return enableRequestCountLimit;
        }

        public void setEnableRequestCountLimit(Boolean enableRequestCountLimit) {
            this.enableRequestCountLimit = enableRequestCountLimit;
        }

        public Integer getMaxRequestCount() {
            return maxRequestCount;
        }

        public void setMaxRequestCount(Integer maxRequestCount) {
            this.maxRequestCount = maxRequestCount;
        }

        public Duration getDefaultTimeSlice() {
            return defaultTimeSlice;
        }

        public void setDefaultTimeSlice(Duration defaultTimeSlice) {
            this.defaultTimeSlice = defaultTimeSlice;
        }
    }

    public MinIoConfig getMinIoConfig() {
        return minIoConfig;
    }

    public void setMinIoConfig(MinIoConfig minIoConfig) {
        this.minIoConfig = minIoConfig;
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

    public QualityOfServiceStrategyConfig getQualityOfServiceStrategyConfig() {
        return qualityOfServiceStrategyConfig;
    }

    public void setQualityOfServiceStrategyConfig(QualityOfServiceStrategyConfig qualityOfServiceStrategyConfig) {
        this.qualityOfServiceStrategyConfig = qualityOfServiceStrategyConfig;
    }
}
