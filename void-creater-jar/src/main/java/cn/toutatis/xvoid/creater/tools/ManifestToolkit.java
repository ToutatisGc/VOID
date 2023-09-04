package cn.toutatis.xvoid.creater.tools;

import cn.toutatis.xvoid.creater.exception.ConfigFileMissingException;
import cn.toutatis.xvoid.creater.mapper.Mapper;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.DataSourceConfig;
import cn.toutatis.xvoid.creater.mybatisplus.generator.config.querys.MySqlQuery;
import cn.toutatis.xvoid.creater.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import com.baomidou.mybatisplus.annotation.DbType;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Toutatis_Gc
 */
public class ManifestToolkit {

    private volatile static ManifestToolkit manifestToolkit;
    private static Logger toolkitLogger = Logger.getLogger(ManifestToolkit.class);

    private ManifestToolkit() {}
    public static ManifestToolkit getInstance(){
        if (manifestToolkit == null){
            synchronized (ManifestToolkit.class){
                if (manifestToolkit == null){
                    manifestToolkit = new ManifestToolkit();
                    manifestToolkit.toolKitInit();
                    toolkitLogger.info("[初始化]初始化生成"+ manifestToolkit);
                }
            }
        }
        return manifestToolkit;
    }

    private File configFile = new File(this.getRootPath() + "/config.properties");
    private Properties configProperties = new Properties();

    private static SqlSessionFactory SQL_EXECUTOR_FACTORY;

    public static String DATE_FORMAT_REGEX = "yyyy-MM-dd HH:mm:ss";
    private static SimpleDateFormat SIMPLE_DATE_FORMAT_STANDARD = new SimpleDateFormat(DATE_FORMAT_REGEX);

    /**
     * Mybatis-plus生成器的数据源
     */
    private DataSourceConfig dataSourceConfig;

    /**
     * 工具箱初始化
     * 加载配置文件
     * 加载mysql驱动
     * 加载mybatis驱动
     */
    private void toolKitInit(){
        try {
            configProperties.load(new FileInputStream(this.configFile));
            String databaseDriver = getConfigProperties("databaseDriver");
            Class.forName(databaseDriver);
            this.initSQLExecutor();
        } catch (IOException | ClassNotFoundException e) {
            toolkitLogger.error("[初始化]初始化配置文件丢失.[0]");
            e.printStackTrace();
        }
    }

    /**
     * @throws IOException 加载配置文件
     */
    private void initSQLExecutor() throws IOException {
        String resource = getConfigProperties("mybatisConfigLocation");
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = builder.build(inputStream);
        SQL_EXECUTOR_FACTORY =  sqlSessionFactory;
    }

    /**
     * @return 获取sqlSessionFactory
     */
    public SqlSessionFactory getSQLExecutorFactory(){
        return SQL_EXECUTOR_FACTORY;
    }

    public SqlSession getSQLExecutorWithConnection() {
        try {
            return SQL_EXECUTOR_FACTORY.openSession(this.getConnect());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Mapper getMapper(Class<? extends Mapper> clazz){
        SqlSession sqlSession = getSQLExecutorWithConnection();
        return sqlSession.getMapper(clazz);
    }

    /**
     * @return 获取系统根路径
     */
    public String getRootPath(){
        return FileToolkit.getRuntimePath(this.getClass());
    }

    /**
     * @param properties 配置文件
     * @param outputFile 输出到文件
     * @param key properties键
     * @param value properties值
     */
    public void saveConfiguration(Properties properties, File outputFile, String key, String value){
        toolkitLogger.info("[配置文件]保存配置KEY:"+key+",VALUE:"+value);
        properties.setProperty(key,value);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            try {
                throw new ConfigFileMissingException(ConfigFileMissingException.MISSING_MESSAGE,e.getCause());
            } catch (ConfigFileMissingException ex) {
                ex.printStackTrace();
            }
        }
        try {
            assert fileOutputStream != null;
            properties.store(fileOutputStream, ConfigurationTable.DELETE_CONFIG_WARNING_INFO_HINT_EN.getInfo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param key 配置健
     * @param value  配置值
     */
    public void saveConfiguration(String key,String value) {
        saveConfiguration(configProperties,configFile,key,value);
    }

    /**
     * @param key 配置健
     * @return 返回config中的值
     */
    public String getConfigProperties(String key){
        return configProperties.getProperty(key);
    }

    public Map<String,Object> testConnect() throws SQLException {
        Connection connection = getConnect();
        Map<String,Object> map = new HashMap<>();
        map.put("connection",connection);
        return map;
    }

    /**
     * @return 获取MySQL-Connect
     * @throws SQLException
     */
    public Connection getConnect() throws SQLException {
        String databaseUrl = getConfigProperties("databaseUrl");
        String password = new String(Base64.getDecoder().decode(configProperties.getProperty("password")));
        return DriverManager.getConnection(databaseUrl,configProperties.getProperty("user"),password);
    }

    /**
     * @param parameters 字符串参数
     * 判断参数里是否有空值
     * @return 有无空值
     */
    public boolean isBlank(String... parameters){
        boolean b = false;
        for (String parameter : parameters) {
            if (parameter == null || "".equals(parameter)){
                b = true;
                break;
            }
        }
        return b;
    }

    public String getCurrentTime(){
        return SIMPLE_DATE_FORMAT_STANDARD.format(new Date());
    }

    public Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey();
            String newKey = underlineToCamel(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    /**
     * @param param 带转换字符串
     * @return 下划线转驼峰字符串
     */
    public String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(param.charAt(i)));
            }
        }
        return sb.toString();
    }
    /**
     * 将List中map的key值命名方式格式化为驼峰
     *
     * @param
     * @param list
     * @return
     */
    public List<Map<String, Object>> formatHumpNameForList(List<Map> list) {
        List<Map<String, Object>> newList = new ArrayList<>();
        for (Map<String, Object> o : list) {
            newList.add(formatHumpName(o));
        }
        return newList;
    }

    public DataSourceConfig getDataSourceConfig(){
        if (this.dataSourceConfig == null){
            DataSourceConfig dataSourceConfig = new DataSourceConfig();
//            默认DbQuery为MYSQL
            dataSourceConfig.setDbQuery(new MySqlQuery());
            dataSourceConfig.setDbType(DbType.MYSQL);
            dataSourceConfig.setDriverName(getConfigProperties("databaseDriver"));
            dataSourceConfig.setUrl(getConfigProperties("databaseUrl"));
            dataSourceConfig.setUsername(getConfigProperties("user"));
            dataSourceConfig.setPassword(new String(Base64.getDecoder().decode(getConfigProperties("password"))));
            dataSourceConfig.setKeyWordsHandler(new MySqlKeyWordsHandler());
            this.dataSourceConfig = dataSourceConfig;
        }
        return this.dataSourceConfig;
    }
}
