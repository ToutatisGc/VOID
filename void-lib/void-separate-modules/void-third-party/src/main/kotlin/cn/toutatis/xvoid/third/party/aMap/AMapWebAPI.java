package cn.toutatis.xvoid.third.party.aMap;

import cn.hutool.http.Method;
import cn.toutatis.xvoid.third.party.aMap.args.WeatherArgs;
import cn.toutatis.xvoid.third.party.basic.ApiDocumentInfo;
import cn.toutatis.xvoid.third.party.basic.BaseAPI;
import cn.toutatis.xvoid.third.party.basic.DocumentMappingEnumPropertyUtils;
import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.toolkit.formatting.StringToolkit;
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import org.jetbrains.annotations.NotNull;

/**
 * @author Toutatis_Gc
 */
@APIDocument(
        name = "高德开放平台",
        description =
                "高德Web服务API向开发者提供HTTP接口，开发者可通过这些接口使用各类型的地理数据服务，返回结果支持JSON和XML格式。 " +
                "Web服务API对所有用户开放。使用本组服务之前，需要申请应用Key。不同类型用户可获取不同的数据访问能力。",
        url = "https://lbs.amap.com/api/webservice/summary",
        version = "4.2.0")
public enum AMapWebAPI implements BaseAPI {

    /**
     * API条目
     */
    @LogEnum @APIDocument(
            description = "查询天气情况1111111"
    )
    CHECK_WEATHER_FORECASTS("查询天气",Method.POST,"https://restapi.amap.com/v3/weather/weatherInfo",new WeatherArgs());


    //对象初始化时，执行下面的方法，将注解上的枚举值解析到cache中
    static {
        DocumentMappingEnumPropertyUtils.mappingProperties(AMapWebAPI.class);
    };

    private final String methodName;

    private final Method method;

    private final String url;

    private final ArgumentsSchema argumentsSchema;


    /**
     * 获取注解的属性
     */
    public ApiDocumentInfo resolve() {
        return DocumentMappingEnumPropertyUtils.resolve(this.name());
    }

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
        ApiDocumentInfo resolve = resolve();
        System.out.printf("""
                ================================================================
                | 类型&方法: [%s.%s]
                |---------------------------------------------------------------
                | API集名称: [ %s ]     版本: [ %s ]
                | 官方网站: [ %s ]
                | API集描述: %s
                |---------------------------------------------------------------
                | 当前请求方法:
                %n""",
                resolve.getApiClass().getSimpleName(),this.name(),
                resolve.getApiSetName(),
                resolve.getApiSetVersion(),
                resolve.getApiSetUrl(),
                StringToolkit.lineWrap("|",3,resolve.getApiSetDescription(), 33)
        );

    }
}
