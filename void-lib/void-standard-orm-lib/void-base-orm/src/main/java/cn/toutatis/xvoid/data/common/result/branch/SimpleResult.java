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

package cn.toutatis.xvoid.data.common.result.branch;

import cn.toutatis.xvoid.data.common.result.Result;
import cn.toutatis.xvoid.data.common.result.ResultCode;
import cn.toutatis.xvoid.toolkit.constant.Time;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode
public class SimpleResult implements Result {


    /**
     * 请求分配ID
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rid;

    /**
     * 响应码枚举
     */
    private String resultCode;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String supportMessage;
    /**
     * 时间戳
     */
    private final long timestamp = System.currentTimeMillis();
    /**
     * 本地时间
     */
    private final String localDateTime = Time.getCurrentTimeByLong(timestamp);
    /**
     * 响应数据
     */
    private Object data;

    public SimpleResult(ResultCode resultCode){
        this.setResultCode(resultCode);
    }

    public SimpleResult(ResultCode resultCode,String message){
        this.setResultCode(resultCode);
        this.setMessage(message);
    }

    public SimpleResult(ResultCode resultCode,String message,Object data){
        this.setResultCode(resultCode);
        this.setMessage(message);
        this.data = data;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode.getCode();
        this.message = resultCode.getInfo();
        this.supportMessage = resultCode.getExtraInfo();
        this.success = resultCode.isSuccess();
    }

    @Override
    public Result serialize() {
        return this;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSupportMessage(String supportMessage) {
        this.supportMessage = supportMessage;
    }

}
