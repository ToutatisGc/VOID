package cn.toutatis.xvoid.creator.tools;

import freemarker.cache.MruCacheStorage;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Toutatis_Gc
 * freeMarker全局配置
 */
public class FreeMarkerConfiguration {

    private static volatile FreeMarkerConfiguration freeMarkerConfiguration = null;
    private static Logger freemarkerLogger = Logger.getLogger(FreeMarkerConfiguration.class);

    private static ManifestToolkit manifestToolkit = ManifestToolkit.getInstance();

    private static Configuration configuration = null;

    private static Map<String ,Object> varies = new HashMap<>(8);

    private FreeMarkerConfiguration(){}
    public static FreeMarkerConfiguration getInstance() {
        if (freeMarkerConfiguration == null){
            synchronized (FreeMarkerConfiguration.class){
                if (freeMarkerConfiguration == null){
                    freeMarkerConfiguration = new FreeMarkerConfiguration();
                    configuration = initConfiguration();
                }
            }
            freemarkerLogger.info("[初始化]模板引擎初始化.");
        }
        return freeMarkerConfiguration;
    }

    private static Configuration initConfiguration(){
        if (configuration == null){
            configuration = new Configuration(Configuration.getVersion());
            try {
                configuration.setDirectoryForTemplateLoading(new File(manifestToolkit.getRootPath()+"/template"));
                configuration.setEncoding(Locale.ENGLISH, manifestToolkit.getConfigProperties("encoding"));
                MruCacheStorage mruCacheStorage = new MruCacheStorage(10, 10);
                configuration.setCacheStorage(mruCacheStorage);
                configuration.setSharedVaribles(varies);
            } catch (IOException e) {
                freemarkerLogger.error("[初始化]模板引擎配置错误.[0]");
                e.printStackTrace();
            } catch (TemplateModelException e) {
                freemarkerLogger.error("[初始化]模板引擎配置错误.[1]");
                e.printStackTrace();
            }
        }
        return configuration;
    }

    public Configuration getConfiguration(){
        return configuration;
    }

    public void setSharedVaries(Map<String ,Object> sharedVaries){
        varies.putAll(sharedVaries);
    }

    public void setSharedVaries(String key,String value){
        varies.put(key,value);
    }
}
