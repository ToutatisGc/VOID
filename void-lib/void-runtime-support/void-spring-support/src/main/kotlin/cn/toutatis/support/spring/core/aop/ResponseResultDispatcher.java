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

package cn.toutatis.support.spring.core.aop;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseResultDispatcher implements ResponseBodyAdvice<Object> {

    @Value("${void.http.result.simple-mode:false}")
    private boolean simpleMode;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  @NotNull MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return body;
//        Class<?> bodyClass = body != null ? body.getClass() : null;
//        if (bodyClass != null){
//            if (bodyClass == ProxyResult.class){
//                Result nBody;
//                ProxyResult proxyResult = (ProxyResult) body;
//                ResultCode resultCode = proxyResult.getResultCode();
//                Boolean useSimpleMode = proxyResult.isUseSimpleMode();
//                if ((useSimpleMode != null && useSimpleMode) || simpleMode) {
//                    nBody = new SimpleResult();
//                } else {
//                    nBody = new DetailedResult();
//                }
//                Object data = proxyResult.getData();
//                if (data != null){
//                    DataIgnore dataIgnoreAnnotation = returnType.getMethodAnnotation(DataIgnore.class);
//                    if (dataIgnoreAnnotation != null){
//
//                    }
//                }
//                return nBody.serialize();
//            }
//        }
//        return body;
    }
}
