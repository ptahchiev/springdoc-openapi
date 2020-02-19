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

package org.springdoc.core;

import java.util.Map;

import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

public class SpringSecurityOAuth2Provider implements SecurityOAuth2Provider {

	private FrameworkEndpointHandlerMapping oauth2EndpointHandlerMapping;

	public SpringSecurityOAuth2Provider(FrameworkEndpointHandlerMapping oauth2EndpointHandlerMapping) {
		this.oauth2EndpointHandlerMapping = oauth2EndpointHandlerMapping;
	}

	public FrameworkEndpointHandlerMapping getOauth2EndpointHandlerMapping() {
		return oauth2EndpointHandlerMapping;
	}

	@Override
	public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
		return oauth2EndpointHandlerMapping.getHandlerMethods();
	}

	@Override
	public Map getFrameworkEndpoints() {
		return oauth2EndpointHandlerMapping.getApplicationContext().getBeansWithAnnotation(FrameworkEndpoint.class);
	}

}
