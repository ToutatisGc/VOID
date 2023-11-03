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

package cn.toutatis.xvoid.common.result.branch;

import cn.toutatis.xvoid.common.result.AbstractResult;
import cn.toutatis.xvoid.common.result.Result;
import cn.toutatis.xvoid.common.result.ResultCode;
import cn.toutatis.xvoid.toolkit.constant.Time;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Toutatis_Gc
 * 详细响应模式
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class DetailedResult extends AbstractResult implements Result {

    /**
     * 请求分配ID
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rid;

    private String resultStatus;
    /**
     * 响应码枚举
     */
    private String resultCode;
    /**
     * 内部分配ID
     */
    private String innerCode;
    /**
     *  是否成功
     */
    private Boolean success;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 一般来源于resultCode的extraInfo字段
     * 或可以另外指定为辅助消息
     */
    private String supportMessage;
    /**
     * 时间戳
     */
    private final long timestamp = Time.getCurrentSeconds();
    /**
     * 本地时间
     */
    private final String localDateTime = Time.getCurrentTimeByLong(timestamp*1000);

    public DetailedResult() { }

    public DetailedResult(ResultCode resultCode){
        this.setResultCode(resultCode);
    }

    public DetailedResult(ResultCode resultCode,String message){
        this.setResultCode(resultCode);
        this.setMessage(message);
    }

    public DetailedResult(ResultCode resultCode,String message,Object data){
        this.setResultCode(resultCode);
        if(message != null){
            this.setMessage(message);
        }
        this.data = data;
    }

    @Override
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode.getCode();
        this.message = resultCode.getInfo();
        this.supportMessage = resultCode.getExtraInfo();
        this.success = resultCode.isSuccess();
        this.resultStatus = resultCode.name();
        this.innerCode = resultCode.getInnerCode();
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSupportMessage(String supportMessage) {
        this.supportMessage = supportMessage;
    }


    @Override
    public Object serialize() {
        return this;
    }

}
