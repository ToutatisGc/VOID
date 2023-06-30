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

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;

/**
 * 如需统一RESTFUL API 则需要统一返回该对象的实例
 * @author Toutatis_Gc
 */
public interface Result extends Serializable {

    /**
     * @return 返回自定义序列化后的对象
     */
    Object serialize();

    /**
     * @param data 返回数据
     */
    void setData(Object data);

    /**
     * @param resultCode 结果代码
     * 此方法用以设置返回的结果代码,不同的结果代码会返回不同效果
     */
    void setResultCode(ResultCode resultCode);

    /**
     * 获取数据内容转换为map类型
     * @return 数据map
     */
    @JsonIgnore
    Map<String,Object> getDataMap();

}
