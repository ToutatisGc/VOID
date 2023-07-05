package cn.toutatis.xvoid.third.party.aMap;

import cn.hutool.http.Method;
import cn.toutatis.xvoid.third.party.aMap.args.IPLocationArgs;
import cn.toutatis.xvoid.third.party.aMap.args.WeatherArgs;
import cn.toutatis.xvoid.third.party.basic.ApiDocumentInfo;
import cn.toutatis.xvoid.third.party.basic.ApiSupport;
import cn.toutatis.xvoid.third.party.basic.BaseAPI;
import cn.toutatis.xvoid.third.party.basic.DocumentMappingEnumPropertyUtils;
import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.toolkit.formatting.StringToolkit;
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import kotlin.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Toutatis_Gc
 */
@APIDocument(
        name = "高德开放平台|高德地图API",
        description =
                "高德Web服务API向开发者提供HTTP接口，开发者可通过这些接口使用各类型的地理数据服务，返回结果支持JSON和XML格式。 " +
                "Web服务API对所有用户开放。使用本组服务之前，需要申请应用Key。不同类型用户可获取不同的数据访问能力。",
        url = "https://lbs.amap.com/api/webservice/summary",
        version = "4.2.0")
public enum AMapWebAPI implements BaseAPI<AMapWebAPI>, ApiSupport {

    /**
     * API条目
     */
    @LogEnum @APIDocument(
            description = "天气查询是一个简单的HTTP接口，根据用户输入的adcode，查询目标区域当前/未来的天气情况，数据来源是中国气象局。",
            url = "https://lbs.amap.com/api/webservice/guide/api/weatherinfo",
            version = "2023年04月03日"
    )
    WEATHER_INQUIRY("天气查询",Method.GET,"https://restapi.amap.com/v3/weather/weatherInfo",new WeatherArgs()),

    @LogEnum @APIDocument(
            description = "IP定位是一套简单的HTTP接口，根据用户输入的IP地址，能够快速的帮用户定位IP的所在位置。IP定位：仅支持IPV4，不支持国外IP解析。",
            url = "https://lbs.amap.com/api/webservice/guide/api/ipconfig",
            version = "2022年03月18日"
    )
    IP_LOCATION("IP定位",Method.GET,"https://restapi.amap.com/v3/ip",new IPLocationArgs()),
    ;

    //初始化解析
    static {
        DocumentMappingEnumPropertyUtils.mappingProperties(AMapWebAPI.class);
    };

    private final String methodName;

    private final Method method;

    private final String url;

    private final ArgumentsSchema argumentsSchema;



    AMapWebAPI(String methodName, Method method, String url, ArgumentsSchema argumentsSchema) {
        this.methodName = methodName;
        this.method = method;
        this.url = url;
        this.argumentsSchema = argumentsSchema;
    }

    @NotNull
    @Override
    public String getMethodName() {
        return methodName;
    }

    @NotNull
    @Override
    public Method getMethod() {
        return method;
    }

    @NotNull
    @Override
    public String getUrl() {
        return url;
    }

    @NotNull
    @Override
    public ArgumentsSchema getArgumentsSchema() {
        return argumentsSchema;
    }

    @Override
    public void printAPI() {
        ApiDocumentInfo resolve = resolve(this.name());
        ArgumentsSchema argumentsSchema = resolve.getArgumentsSchema();
        List<Triple<String, Boolean, String>> allParameters = argumentsSchema.getAllParameters();
        StringBuilder sb = new StringBuilder("");
        if (allParameters!= null && !allParameters.isEmpty()){
            for (int i = 0; i < allParameters.size(); i++) {
                Triple<String, Boolean, String> parameter = allParameters.get(i);
                if (i!= 0){
                    sb.append("|");
                    sb.append("\t".repeat(2));
                }
                sb.append(i+1).append(".");
                sb.append(parameter.component2() ? "[必填]" : "[选填]");
                sb.append(parameter.component1()).append(" : ").append(parameter.component3());
                if (i != allParameters.size() - 1){
                    sb.append("\r\n");
                }
            }
        }
        System.out.printf("""
                ================================================================
                | 类型&方法: [%s.%s]
                |---------------------------------------------------------------
                | API集名称: [ %s ]     版本: [ %s ]
                | 官方网站: [ %s ]
                | API集描述: %s
                |---------------------------------------------------------------
                | 当前请求方法: [ %s ]     版本: [ %s ]
                | API地址: [ %s ]
                | 方法说明: %s
                | 请求类型: [ %s ] URL: [ %s ]
                | 请求参数:
                |       %s
                |================================================================
                %n""",
                resolve.getApiClass().getSimpleName(),this.name(),
                resolve.getApiSetName(),
                resolve.getApiSetVersion(),
                resolve.getApiSetUrl(),
                StringToolkit.lineWrap("|",3,resolve.getApiSetDescription(), 33),
                resolve.getMethodName(),
                resolve.getMethodVersion(),
                resolve.getMethodUrl(),
                StringToolkit.lineWrap("|",3,resolve.getMethodDescription(), 33),
                resolve.getRequestMethod(),
                resolve.getMethodUrl(),
                sb.toString()
        );
    }

}
