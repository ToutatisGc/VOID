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


package cn.toutatis.xvoid.data.common.result;

/**
 * @author Toutatis_Gc
 * 请求统一响应结果
 */
public enum ResultCode implements Cloneable {

    /**
     * 操作部分[SUCCESS]
     * 2****** 正操操作
     * 3****** 外部操作
     */
    NORMAL_SUCCESS("200000","OP_REQ_000000_SC",true,"请求成功","HTTP请求成功"),
    NORMAL_SUCCESS_BUT_NULL("200000","OP_REQ_000000_FL",true,"请求成功[空数据]","请求成功但为空数据"),
    UPDATE_SUCCESS("200001","OP_UPD_000001_SC",true,"更新成功","更新[{}]操作成功"),
    INSERT_SUCCESS("200002","OP_ADD_000002_SC",true,"添加成功","添加[{}]操作成功"),
    DELETE_SUCCESS("200003","OP_DEL_000003_SC",true,"删除成功","删除[{}]操作成功"),
    APPLY_SUCCESS("200004","OP_APL_000004_SC",true,"审核成功","审核[{}]操作成功"),
    UPLOAD_SUCCESS("200005","UP_ADD_000005_SC",true,"上传成功","上传[{}]文件成功"),
    REQUEST_SUCCESS("320001","REQ_THIRD_320001_SC",true,"发送请求成功","向第三方接口[{}]发送请求成功"),
    /**
     * 操作部分[FAILED]
     * 5****** 失败操作
     */
    NORMAL_FAILED("500000","OP_REQ_500000_FL",false,"操作失败","HTTP请求失败"),
    UPDATE_FAILED("500001","OP_UPD_500001_FL",false,"更新失败","操作[{}]失败"),
    INSERT_FAILED("500002","OP_ADD_500002_FL",false,"添加失败","操作[{}]失败"),
    DELETE_FAILED("500003","OP_DEL_500003_FL",false,"删除失败","操作[{}]失败"),
    APPLY_FAILED("500004","OP_APL_500004_FL",false,"审核失败","操作[{}]失败"),
    UPLOAD_FAILED("500005","UP_ADD_500005_FL",false,"上传失败","上传[{}]文件失败"),
    NOT_HAVE_SELECT_DATA("500404","OP_FOM_500404_FL",false,"数据为空","必要表单数据为空"),
    REQUEST_FAILED("350001","REQ_THIRD_350001_SC",false,"发送请求失败","向第三方[{}]发送请求失败"),
    WRONG_DATA("500500","OP_ERR_500500_FL",false,"错误数据","查找不到对应数据"),
    /**
     * 权限部分 9******
     */
    NOT_BOUNDS_DATA("900406","BD_NOT_900406_FL",false,"未绑定数据","必须绑定关键数据才视为请求成功"),
    ANONYMITY_FAILED("900000","0P_ANO_900000_FL",false,"拒绝匿名访问","匿名无权限访问"),
    CHECKED_FAILED("900001","0P_TKN_900001_FL",false,"校验失败","验证权限失败"),
    PERMISSION_DEFINED_FAILED("900002","0P_AUTH_900002_FL",false,"权限不足","已认证权限不足"),
    AUTHENTICATION_FAILED("900003","0P_AUTH_900003_FL",false,"认证失败","用户名或密码错误"),
    AUTHENTICATION_SUCCESSFUL("200000","0P_AUTH_999999_SC",true,"认证成功","认证成功"),
    LOGOUT_SUCCESSFUL("200000","0P_AUTH_888888_SC",true,"注销成功","注销成功"),
    NOT_LOGGED_IN_ERROR("900404","0P_AUTH_900404_SC",true,"尚未登录","尚未登录且注销"),

    /**
     * 系统部分
     */
    UNKNOWN_EXCEPTION("UNKNOWN","0P_UNKNOWN_FAILED",false,"未知请求","未知请求"),
    NULL_DIRECT_CODE("404001","0P_UNKNOWN_DIRECT",false,"未指定类型","没有指定MESSAGE"),
    MISSING_PARAMETER("404002","0P_PAM_404002_FL",false,"缺少参数","缺少参数"),
    NOT_TENANT("404003","BD_NOT_404003_FL",false,"未绑定子账户","操作失败[平台下必须绑定子用户]"),
    NOT_FOUND("404000","OP_NOT_404000_FL",false,"请求未找到","请求未找到"),
    REQUEST_EXCEPTION("500000","OP_ERR_500000_FL",false,"发生错误","请求异常"),
    INNER_EXCEPTION("500001","OP_ERR_500001_FL",false,"内部错误","内部运行发生错误"),
    ILLEGAL_OPERATION("901000","OP_LAW_901000_FL",false,"非法请求[行为已记录]","越权操作"),
    NOT_ALLOW_REQUEST_METHOD("405000","OP_LAW_405000_FL",false,"不允许此方法访问","仅允许[POST,GET]"),
    DATA_EXPIRED("404003","OP_DAT_404003_FL",false,"数据已失效","数据过期"),
    JUST_SHOW("971111","OP_DAT_971111_FL",false,"展示模式不可操作","展示模式不可操作"),
    REMOTE_SERVICE_CANT_ACCESS("500002","REQ_THIRD_500002_FL",false,"服务链错误","远程接口出现问题."),
    REMOTE_ACCESS_TIME_OUT("500003","REQ_THIRD_500003_FL",false,"当前访问人数过多,请稍候再试.","远程接口连接超时."),
    API_UNREALIZED("UNREALIZED","0P_UNREALIZED_FAILED",false,"接口未实现","服务接口未实现."),
    ;

    /**
     * 响应码
     */
    private final String code;

    /**
     * 内部响应码
     * 一般不显示
     */
    private final String innerCode;

    /**
     * 当前code所代表响应信息
     */
    private final String info;

    /**
     * 额外信息
     */
    private final String extraInfo;

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

    public String getInnerCode() { return innerCode; }

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
