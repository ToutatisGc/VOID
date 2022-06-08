/*
 *    Copyright 2021 Toutatis_Gc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package cn.toutatis.data.common;

/**
 * @author Toutatis_Gc
 * 请求统一响应结果
 */
public enum ResultCode implements Cloneable {

    /*TODO 加占位*/
    /**
     * 操作部分
     */
    NORMAL_SUCCESS("000000","000000",true,"请求成功","HTTP请求成功"),
    UPDATE_SUCCESS("000000","000001",true,"更新成功","正常操作"),
    INSERT_SUCCESS("000000","000006",true,"添加成功","正常操作"),
    DELETE_SUCCESS("000000","000002",true,"删除成功","正常操作"),
    APPLY_SUCCESS("000000","000005",true,"审核成功","正常操作"),
    UPLOAD_SUCCESS("000000","000007",true,"上传成功","上传文件成功"),
    REQUEST_SUCCESS("000001","000003",true,"发送请求成功","向第三方发送请求"),
    NORMAL_SUCCESS_BUT_NULL("000000","000004",true,"请求成功[空数据]","请求成功但为空数据"),

    NORMAL_FAILED("100000","100000",false,"操作失败","操作失败"),
    UPDATE_FAILED("100000","100001",false,"更新失败","操作失败"),
    INSERT_FAILED("100000","100005",false,"添加失败","操作失败"),
    DELETE_FAILED("100000","100002",false,"删除失败","操作失败"),
    APPLY_FAILED("100000","100004",false,"审核失败","操作失败"),
    REQUEST_FAILED("100001","100003",false,"发送请求失败","向第三方发送请求失败"),
    UPLOAD_FAILED("100000","100007",false,"上传失败","上传失败"),
    NOT_HAVE_SELECT_DATA("100002","100006",false,"数据为空","必要表单数据为空"),
    WRONG_DATA("100500","100500",false,"错误数据","查找不到对应数据"),
    NOT_BOUNDS_DATA("100000","100404",false,"未绑定数据","必须绑定关键数据才视为请求成功"),


    /**
     * 权限部分
     */
    ANONYMITY_FAILED("900000","900000",false,"拒绝匿名访问","匿名无权限访问"),
    CHECKED_FAILED("900001","900001",false,"校验失败","TOKEN或验证失败"),
    PERMISSION_DEFINED_FAILED("900002","900002",false,"权限不足","已认证权限不足"),
    AUTHENTICATION_FAILED("900003","900003",false,"认证失败","用户名或密码错误"),
    AUTHENTICATION_SUCCESSFUL("000000","999999",true,"认证成功","认证成功"),
    LOGOUT_SUCCESSFUL("000000","888888",true,"注销成功","注销成功"),
    NOT_LOGGED_IN_ERROR("888880","888880",true,"尚未登录","尚未登录且注销"),

    /**
     * 系统部分
     */
    UNKNOWN_EXCEPTION("-1","-1",false,"未知请求","未知请求"),
    NULL_DIRECT_CODE("404001","404001",false,"未指定类型","没有指定MESSAGE"),
    MISSING_PARAMETER("404002","404002",false,"缺少参数","缺少参数"),
    NOT_FOUND("404000","404000",false,"请求未找到","请求未找到"),
    REQUEST_EXCEPTION("500000","500000",false,"发生错误","请求异常"),
    INNER_EXCEPTION("500001","500001",false,"内部错误","内部运行发生错误"),
    ILLEGAL_OPERATION("901000","901000",false,"非法请求[行为已记录]","越权操作"),
    NOT_ALLOW_REQUEST_METHOD("405000","405000",false,"不允许此方法访问","仅允许[POST,GET]"),
    DATA_EXPIRED("404003","404003",false,"数据已失效","数据过期"),
    JUST_SHOW("971111","971111",false,"展示模式不可操作","展示模式不可操作"),
    REMOTE_SERVICE_CANT_ACCESS("500002","500002",false,"服务链错误","远程接口出现问题."),
    REMOTE_ACCESS_TIME_OUT("500003","500003",false,"当前访问人数过多,请稍候再试.","远程接口连接超时."),
    API_UNREALIZED("UNSAFE","UNSAFE",false,"接口未实现","微服务接口未实现具体方法."),
    ;

    /**
     * 响应码
     */
    private String code;

    /**
     * 内部响应码
     * 一般不显示
     */
    private String innerCode;

    /**
     * 当前code所代表响应信息
     */
    private String info;

    /**
     * 额外信息
     */
    private String extraInfo;

    /**
     * 是否成功
     */
    private final boolean success;

    ResultCode(String code, String innerCode, boolean success, String info, String extraInfo) {
        this.code = code;
        this.innerCode = innerCode;
        this.info = info;
        this.extraInfo = extraInfo;
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public boolean isSuccess() {
        return success;
    }

}
