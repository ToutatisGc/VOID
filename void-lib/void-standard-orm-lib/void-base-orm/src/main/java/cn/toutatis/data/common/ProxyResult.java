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

import cn.toutatis.data.implement.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProxyResult implements Result {

    private ResultCode resultCode;

    private String message;

    private Object data;

    @JsonIgnore
    private Boolean useSimpleMode = null;

    @JsonIgnore
    private boolean autoConfig;

    public ProxyResult(boolean useSimpleMode, boolean autoConfig) {
        this.useSimpleMode = useSimpleMode;
        this.autoConfig = autoConfig;
    }

    public ProxyResult(boolean useSimpleMode) {
        this.useSimpleMode = useSimpleMode;
    }

    public ProxyResult(ResultCode resultCode, String message, Object data) {
        this.resultCode = resultCode;
        this.message = message;
        this.data = data;
    }

    public Boolean isUseSimpleMode() {
        return useSimpleMode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Object serialize() {
        return this;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    public void setData(Object data, boolean autoConfig){
        this.setData(data);
        this.autoConfig = autoConfig;
    }

    public Object getData() {
        return data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
