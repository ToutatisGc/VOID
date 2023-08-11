package cn.toutatis.xvoid.spring.support.enhance.mapping;

import cn.toutatis.xvoid.spring.support.Meta;
import cn.toutatis.xvoid.spring.configure.system.enums.global.RunMode;
import cn.toutatis.xvoid.spring.configure.system.VoidGlobalConfiguration;
import cn.toutatis.xvoid.toolkit.file.FileToolkit;
import cn.toutatis.xvoid.toolkit.formatting.JsonToolkit;
import cn.toutatis.xvoid.toolkit.log.LoggerToolkit;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Toutatis_Gc
 * @date 2022/11/14 15:12
 * 安全和静态资源解析器
 * 文件使用 xvoid-resources-mapping.json 命名,格式为JSON
 * 配置文件内容分为 RESOURCES 和 SECURITY 两部分
 */
@Component
public class XvoidMappingResolver {

    private final JSONObject resourcesMapping = new JSONObject(2);

    private final Logger logger = LoggerToolkit.getLogger(this.getClass());

    private final VoidGlobalConfiguration voidGlobalConfiguration;

    public XvoidMappingResolver(VoidGlobalConfiguration voidGlobalConfiguration) {
        File file = null;
        URL resourcesFile = FileToolkit.getResourcesFile("config/xvoid-resources-mapping.json");
        if (resourcesFile != null){
            file = new File(resourcesFile.getFile());
        }
        if (file == null || !file.exists()){
            resourcesFile = FileToolkit.getResourcesFile("xvoid-resources-mapping.json");
            if (resourcesFile != null){
                file = new File(resourcesFile.getFile());
            }
            if (file == null || !file.exists()){
                logger.warn("资源映射配置将默认使用[xvoid-spring-support]模块下的resources/config/xvoid-resources-mapping-default.json文件作为默认行为," +
                        "请参考此文件在开发框架下创建resources/config/xvoid-resources-mapping.json[*config文件夹下优先级更高]" +
                        "或resources/xvoid-resources-mapping-default.json文件作为资源和安全部分映射管理.");
                try {
                    file = new File(Objects.requireNonNull(FileToolkit.getResourcesFile("config/xvoid-resources-mapping-default.json")).toURI());
                } catch (URISyntaxException e) { e.printStackTrace(); }
            }
        }
        if (file != null){
            JSONObject fileContent = JsonToolkit.parseJsonObject(file);
            resourcesMapping.putAll(fileContent);
        }
        this.voidGlobalConfiguration = voidGlobalConfiguration;
    }

    /**
     * 由配置文件转换过来的键值对
     * @return 获取静态资源映射
     */
    public List<ResourcesMapping> getStaticResourcesMapping(){
        JSONObject staticResourcesMappingObject = resourcesMapping.getJSONObject("RESOURCES");
        ArrayList<ResourcesMapping> resourcesMappings = new ArrayList<>(staticResourcesMappingObject.keySet().size());
        for (String key : staticResourcesMappingObject.keySet()) {
            List<String> locations = staticResourcesMappingObject.getJSONArray(key).toJavaList(String.class);
            logger.info("[{}]解析静态资源路径:{},资源位置:{}", Meta.MODULE_NAME,key,locations);
            ResourcesMapping resourcesMapping = new ResourcesMapping(key, locations);
            resourcesMappings.add(resourcesMapping);
        }
        return resourcesMappings;
    }

    /**
     * @return 获取无权限访问路径
     */
    public String[] getSecurityPermittedPaths() {
        RunMode mode = voidGlobalConfiguration.getMode();
        logger.info("[{}]当前启动模式为:{}", Meta.MODULE_NAME,mode);
        String[] openMappings = new String[1];
        if (mode == RunMode.DEBUG){
            logger.warn("[{}]注意开发模式为:DEBUG[调试模式],将忽略所有权限控制.", Meta.MODULE_NAME);
            openMappings[0] = "/**";
            return openMappings;
        }
        JSONObject securityMapping = resourcesMapping.getJSONObject("SECURITY");
        JSONArray openArr = securityMapping.getJSONArray("OPEN");
        logger.info("[" + Meta.MODULE_NAME + "] 添加OPEN开放路径权限：" + openArr);
        ArrayList<String> pathArr = new ArrayList<>(openArr.toJavaList(String.class));
        JSONArray vipArr = securityMapping.getJSONArray("VIP");
        logger.info("[" + Meta.MODULE_NAME + "] 添加重要开放路径权限：" + vipArr);
        pathArr.addAll(vipArr.toJavaList(String.class));
        JSONArray runModePathArr = securityMapping.getJSONArray(mode.name());
        logger.info("["+ Meta.MODULE_NAME+"] 添加{}模式开放路径权限：{}",mode.name(),runModePathArr);
        pathArr.addAll(runModePathArr.toJavaList(String.class));
        openMappings = pathArr.toArray(openMappings);
        return openMappings;
    }

    /**
     * 静态资源映射类
     */
    public static class ResourcesMapping {

        public ResourcesMapping(String mapping, List<String> locations) {
            this.mapping = mapping;
            this.locations = locations;
        }

        private final String mapping;

        private final List<String> locations;

        public String getMapping() {
            return mapping;
        }

        public List<String> getLocations() {
            return locations;
        }
    }
}
