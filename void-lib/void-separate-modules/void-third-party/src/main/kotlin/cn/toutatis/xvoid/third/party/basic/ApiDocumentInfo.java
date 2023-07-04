package cn.toutatis.xvoid.third.party.basic;

import cn.toutatis.xvoid.third.party.basic.annotations.APIDocument;
import cn.toutatis.xvoid.toolkit.http.base.ArgumentsSchema;
import cn.toutatis.xvoid.toolkit.log.LogEnum;
import lombok.Data;

/**
 * 文档信息
 * @author Toutatis_Gc
 */
@Data
public class ApiDocumentInfo {

    /**
     * API集名称
     */
    private String apiSetName;

    /**
     * API集描述
     */
    private String apiSetDescription;

    /**
     * API集版本
     */
    private String apiSetVersion;

    /**
     * API集官网地址
     */
    private String apiSetUrl;

    /**
     * 当前方法名称
     */
    private String methodName;

    /**
     * 方法描述
     */
    private String methodDescription;

    /**
     * 方法请求地址
     */
    private String methodUrl;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 参数说明
     */
    private ArgumentsSchema argumentsSchema;

    /**
     * 方法版本
     */
    private String methodVersion;

    /**
     * API类
     */
    private Class<?> apiClass;

    private LogEnum logEnum;

    /**
     * API总说明
     */
    private APIDocument apiSetDocument;

    /**
     * API方法说明
     */
    private APIDocument apiDocument;
    }