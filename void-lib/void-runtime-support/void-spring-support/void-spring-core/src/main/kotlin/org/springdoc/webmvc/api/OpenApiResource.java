/*
 *
 *  *
 *  *  *
 *  *  *  * Copyright 2019-2022 the original author or authors.
 *  *  *  *
 *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  * You may obtain a copy of the License at
 *  *  *  *
 *  *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *
 *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  * See the License for the specific language governing permissions and
 *  *  *  * limitations under the License.
 *  *  *
 *  *
 *
 */

package org.springdoc.webmvc.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.core.util.PathUtils;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.core.*;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.fn.RouterOperation;
import org.springdoc.core.providers.ActuatorProvider;
import org.springdoc.core.providers.RepositoryRestResourceProvider;
import org.springdoc.core.providers.SecurityOAuth2Provider;
import org.springdoc.core.providers.SpringWebProvider;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.springdoc.core.Constants.DEFAULT_GROUP_NAME;
import static org.springdoc.core.providers.ActuatorProvider.getTag;

/**
 * The type Web mvc open api resource.
 * @author bnasslahsen, Azige
 */
public abstract class OpenApiResource extends AbstractOpenApiResource {

	/**
	 * Instantiates a new Open api resource.
	 *
	 * @param groupName the group name
	 * @param openAPIBuilderObjectFactory the open api builder object factory
	 * @param requestBuilder the request builder
	 * @param responseBuilder the response builder
	 * @param operationParser the operation parser
	 * @param springDocConfigProperties the spring doc config properties
	 * @param springDocProviders the spring doc providers
	 * @param springDocCustomizers the spring doc customizers
	 */
	public OpenApiResource(String groupName, ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory, AbstractRequestService requestBuilder,
			GenericResponseService responseBuilder, OperationService operationParser,
			SpringDocConfigProperties springDocConfigProperties,
			SpringDocProviders springDocProviders, SpringDocCustomizers springDocCustomizers) {
		super(groupName, openAPIBuilderObjectFactory, requestBuilder, responseBuilder, operationParser,
				 springDocConfigProperties, springDocProviders, springDocCustomizers);
	}

	/**
	 * Instantiates a new Open api resource.
	 *
	 * @param openAPIBuilderObjectFactory the open api builder object factory
	 * @param requestBuilder the request builder
	 * @param responseBuilder the response builder
	 * @param operationParser the operation parser
	 * @param springDocConfigProperties the spring doc config properties
	 * @param springDocProviders the spring doc providers
	 * @param springDocCustomizers the spring doc customizers
	 */
	public OpenApiResource(ObjectFactory<OpenAPIService> openAPIBuilderObjectFactory, AbstractRequestService requestBuilder,
			GenericResponseService responseBuilder, OperationService operationParser,
			SpringDocConfigProperties springDocConfigProperties,
			SpringDocProviders springDocProviders,  SpringDocCustomizers springDocCustomizers) {
		super(DEFAULT_GROUP_NAME, openAPIBuilderObjectFactory, requestBuilder, responseBuilder, operationParser,
			 springDocConfigProperties, springDocProviders, springDocCustomizers);
	}

	/**
	 * Openapi json string.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @param locale the locale
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	public byte[]  openapiJson(HttpServletRequest request,
			String apiDocsUrl, Locale locale)
			throws JsonProcessingException {
		calculateServerUrl(request, apiDocsUrl, locale);
		OpenAPI openAPI = this.getOpenApi(locale);
		return writeJsonValue(openAPI);
	}

	/**
	 * Openapi yaml string.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @param locale the locale
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	public byte[] openapiYaml(HttpServletRequest request,
			String apiDocsUrl, Locale locale)
			throws JsonProcessingException {
		calculateServerUrl(request, apiDocsUrl, locale);
		OpenAPI openAPI = this.getOpenApi(locale);
		return writeYamlValue(openAPI);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void getPaths(Map<String, Object> restControllers, Locale locale, OpenAPI openAPI) {
		Optional<SpringWebProvider> springWebProviderOptional = springDocProviders.getSpringWebProvider();
		springWebProviderOptional.ifPresent(springWebProvider -> {
			Map<RequestMappingInfo, HandlerMethod> map = springWebProvider.getHandlerMethods();

			Optional<RepositoryRestResourceProvider> repositoryRestResourceProviderOptional = springDocProviders.getRepositoryRestResourceProvider();
			repositoryRestResourceProviderOptional.ifPresent(restResourceProvider -> {
				List<RouterOperation> operationList = restResourceProvider.getRouterOperations(openAPI, locale);
				calculatePath(operationList, locale, openAPI);
				restResourceProvider.customize(openAPI);
				Map<RequestMappingInfo, HandlerMethod> mapDataRest = restResourceProvider.getHandlerMethods();
				Map<String, Object> requestMappingMap = restResourceProvider.getBasePathAwareControllerEndpoints();
				Class[] additionalRestClasses = requestMappingMap.values().stream().map(AopUtils::getTargetClass).toArray(Class[]::new);
				AbstractOpenApiResource.addRestControllers(additionalRestClasses);
				map.putAll(mapDataRest);
			});

			Optional<ActuatorProvider> actuatorProviderOptional = springDocProviders.getActuatorProvider();
			if (actuatorProviderOptional.isPresent() && springDocConfigProperties.isShowActuator()) {
				Map<RequestMappingInfo, HandlerMethod> actuatorMap = actuatorProviderOptional.get().getMethods();
				this.openAPIService.addTag(new HashSet<>(actuatorMap.values()), getTag());
				map.putAll(actuatorMap);
			}
			calculatePath(restControllers, map, locale, openAPI);
		});

		Optional<SecurityOAuth2Provider> securityOAuth2ProviderOptional = springDocProviders.getSpringSecurityOAuth2Provider();
		if (securityOAuth2ProviderOptional.isPresent()) {
			SecurityOAuth2Provider securityOAuth2Provider = securityOAuth2ProviderOptional.get();
			Map<RequestMappingInfo, HandlerMethod> mapOauth = securityOAuth2Provider.getHandlerMethods();
			Map<String, Object> requestMappingMapSec = securityOAuth2Provider.getFrameworkEndpoints();
			Class[] additionalRestClasses = requestMappingMapSec.values().stream().map(AopUtils::getTargetClass).toArray(Class[]::new);
			AbstractOpenApiResource.addRestControllers(additionalRestClasses);
			calculatePath(requestMappingMapSec, mapOauth, locale, openAPI);
		}

		springDocProviders.getRouterFunctionProvider().ifPresent(routerFunctions -> routerFunctions.getRouterFunctionPaths()
				.ifPresent(routerBeans -> routerBeans.forEach((beanName, routerFunctionVisitor) -> getRouterFunctionPaths(beanName, routerFunctionVisitor, locale, openAPI))));
	}

	/**
	 * Calculate path.
	 *
	 * @param restControllers the rest controllers
	 * @param map the map
	 * @param locale the locale
	 * @param openAPI the open api
	 */
	protected void calculatePath(Map<String, Object> restControllers, Map<RequestMappingInfo, HandlerMethod> map, Locale locale, OpenAPI openAPI) {
		TreeMap<RequestMappingInfo, HandlerMethod> methodTreeMap = new TreeMap<>(byReversedRequestMappingInfos());
		methodTreeMap.putAll(map);
		Optional<SpringWebProvider> springWebProviderOptional = springDocProviders.getSpringWebProvider();
		springWebProviderOptional.ifPresent(springWebProvider -> {
			for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : methodTreeMap.entrySet()) {
				RequestMappingInfo requestMappingInfo = entry.getKey();
				HandlerMethod handlerMethod = entry.getValue();
				Set<String> patterns = springWebProvider.getActivePatterns(requestMappingInfo);
				if (!CollectionUtils.isEmpty(patterns)) {
					Map<String, String> regexMap = new LinkedHashMap<>();
					for (String pattern : patterns) {
						String operationPath = PathUtils.parsePath(pattern, regexMap);
						String[] produces = requestMappingInfo.getProducesCondition().getProducibleMediaTypes().stream().map(MimeType::toString).toArray(String[]::new);
						String[] consumes = requestMappingInfo.getConsumesCondition().getConsumableMediaTypes().stream().map(MimeType::toString).toArray(String[]::new);
						String[] headers = requestMappingInfo.getHeadersCondition().getExpressions().stream().map(Object::toString).toArray(String[]::new);
						String[] params = requestMappingInfo.getParamsCondition().getExpressions().stream().map(Object::toString).toArray(String[]::new);
						if ((isRestController(restControllers, handlerMethod, operationPath) || isActuatorRestController(operationPath, handlerMethod))
								&& isFilterCondition(handlerMethod, operationPath, produces, consumes, headers)) {
							Set<RequestMethod> requestMethods = requestMappingInfo.getMethodsCondition().getMethods();
							// default allowed requestmethods
							if (requestMethods.isEmpty())
								requestMethods = this.getDefaultAllowedHttpMethods();
							calculatePath(handlerMethod, operationPath, requestMethods, consumes, produces, headers, params, locale, openAPI);
						}
					}
				}
			}

		});
	}


	/**
	 * By reversed request mapping infos comparator.
	 *
	 * @return the comparator
	 */
	private Comparator<RequestMappingInfo> byReversedRequestMappingInfos() {
		return (o2, o1) -> o1.toString().compareTo(o2.toString());
	}

	/**
	 * Calculate server url.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @param locale the locale
	 */
	protected void calculateServerUrl(HttpServletRequest request, String apiDocsUrl, Locale locale) {
		super.initOpenAPIBuilder(locale);
		String calculatedUrl = getServerUrl(request, apiDocsUrl);
		openAPIService.setServerBaseUrl(calculatedUrl);
	}

	/**
	 * Gets server url.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @return the server url
	 */
	protected abstract String getServerUrl(HttpServletRequest request, String apiDocsUrl);

}
