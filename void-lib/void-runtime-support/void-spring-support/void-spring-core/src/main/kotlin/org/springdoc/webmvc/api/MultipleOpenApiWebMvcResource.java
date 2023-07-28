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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.*;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static org.springdoc.core.Constants.*;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;

/**
 * The type Multiple open api resource.
 * @author bnasslahsen
 */
@RestController
public class MultipleOpenApiWebMvcResource extends MultipleOpenApiResource {

	/**
	 * Instantiates a new Multiple open api resource.
	 *
	 * @param groupedOpenApis the grouped open apis
	 * @param defaultOpenAPIBuilder the default open api builder
	 * @param requestBuilder the request builder
	 * @param responseBuilder the response builder
	 * @param operationParser the operation parser
	 * @param springDocConfigProperties the spring doc config properties
	 * @param springDocProviders the spring doc providers
	 * @param springDocCustomizers the spring doc customizers
	 */
	public MultipleOpenApiWebMvcResource(List<GroupedOpenApi> groupedOpenApis, ObjectFactory<OpenAPIService> defaultOpenAPIBuilder,
			AbstractRequestService requestBuilder, GenericResponseService responseBuilder, OperationService operationParser,
			SpringDocConfigProperties springDocConfigProperties, SpringDocProviders springDocProviders, SpringDocCustomizers springDocCustomizers) {
		super(groupedOpenApis, defaultOpenAPIBuilder, requestBuilder, responseBuilder, operationParser, springDocConfigProperties, springDocProviders, springDocCustomizers);
	}

	@Autowired
	private OpenAPI openAPI;

	/**
	 * Openapi json string.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @param group the group
	 * @param locale the locale
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	@Operation(hidden = true)
	@GetMapping(value = API_DOCS_URL + "/{group}", produces = MediaType.APPLICATION_JSON_VALUE)
	public byte[] openapiJson(HttpServletRequest request, @Value(API_DOCS_URL) String apiDocsUrl,
			@PathVariable String group, Locale locale)
			throws JsonProcessingException {
		return getOpenApiResourceOrThrow(group).openapiJson(request, apiDocsUrl + DEFAULT_PATH_SEPARATOR + group, locale);
	}

	/**
	 * Openapi yaml string.
	 *
	 * @param request the request
	 * @param apiDocsUrl the api docs url
	 * @param group the group
	 * @param locale the locale
	 * @return the string
	 * @throws JsonProcessingException the json processing exception
	 */
	@Operation(hidden = true)
	@GetMapping(value = DEFAULT_API_DOCS_URL_YAML + "/{group}", produces = APPLICATION_OPENAPI_YAML)
	public byte[] openapiYaml(HttpServletRequest request, @Value(DEFAULT_API_DOCS_URL_YAML) String apiDocsUrl,
			@PathVariable String group, Locale locale)
			throws JsonProcessingException {
		return getOpenApiResourceOrThrow(group).openapiYaml(request, apiDocsUrl + DEFAULT_PATH_SEPARATOR + group, locale);
	}

}
