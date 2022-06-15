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

package cn.toutatis.data.branch.result;

import cn.toutatis.data.common.result.ResultCode;
import cn.toutatis.data.implement.Result;

import java.util.Objects;

public class SimpleResult implements Result {

    private String code;

    private String message;

    private Object data;

    private ResultCode resultCode;

    @Override
    public Result serialize() {
        return this;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleResult that = (SimpleResult) o;
        return Objects.equals(code, that.code) && Objects.equals(message, that.message) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, data);
    }


}
