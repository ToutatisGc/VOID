package cn.toutatis.xvoid.third.party.aMap;

import cn.hutool.http.Method;
import cn.toutatis.xvoid.third.party.aMap.args.WeatherArgs;
import cn.toutatis.xvoid.third.party.basic.ApiDocumentInfo;
import cn.toutatis.xvoid.third.party.basic.BaseAPI;
import cn.toutatis.xvoid.third.party.basic.MappingEnumPropertyUtils;
import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.third.party.openai.Cost;
import cn.toutatis.xvoid.third.party.openai.StudyEnum;
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Toutatis_Gc
 */
public enum AMapAPI implements BaseAPI {

    /**
     * API条目
     */
    @LogEnum @APIDocument(description = "查询天气情况1111111")
    CHECK_WEATHER_FORECASTS("查询天气",Method.POST,"https://restapi.amap.com/v3/weather/weatherInfo",new WeatherArgs());


    //对象初始化时，执行下面的方法，将注解上的枚举值解析到cache中
    static {
        MappingEnumPropertyUtils.mappingProperties(AMapAPI.class);
    };

    private final String methodName;

    private final Method method;

    private final String url;

    private final ArgumentsSchema argumentsSchema;


    /**
     * 获取注解的属性
     */
    public ApiDocumentInfo resolve() {
        return MappingEnumPropertyUtils.resolve(this.name());
    }

    AMapAPI(String methodName, Method method, String url, ArgumentsSchema argumentsSchema) {
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

    }
}
