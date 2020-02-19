/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.springdoc.api;

import java.util.Map;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.Constants;

import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;


public class ActuatorProvider {

	private final WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping;

	public ActuatorProvider(WebMvcEndpointHandlerMapping webMvcEndpointHandlerMapping) {
		this.webMvcEndpointHandlerMapping = webMvcEndpointHandlerMapping;
	}

	public Map<RequestMappingInfo, HandlerMethod> getMethods() {
		return webMvcEndpointHandlerMapping.getHandlerMethods();
	}

	public Tag getTag() {
		Tag actuatorTag = new Tag();
		actuatorTag.setName(Constants.SPRINGDOC_ACTUATOR_TAG);
		actuatorTag.setDescription(Constants.SPRINGDOC_ACTUATOR_DESCRIPTION);
		actuatorTag.setExternalDocs(
				new ExternalDocumentation()
						.url(Constants.SPRINGDOC_ACTUATOR_DOC_URL)
						.description(Constants.SPRINGDOC_ACTUATOR_DOC_DESCRIPTION)
		);
		return actuatorTag;
	}

	public boolean isRestController(String operationPath) {
		return operationPath.startsWith(AntPathMatcher.DEFAULT_PATH_SEPARATOR);
	}

}
