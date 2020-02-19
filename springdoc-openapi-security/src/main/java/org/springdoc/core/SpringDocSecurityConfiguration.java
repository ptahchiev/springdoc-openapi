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

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;

import static org.springdoc.core.Constants.SPRINGDOC_ENABLED;

@Configuration
@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
public class SpringDocSecurityConfiguration {

	@Bean
	@Primary
	IgnoredParameterWithSecurity ignoredParameterAnnotationsWithSecurity() {
		return new IgnoredParameterWithSecurity();
	}

	@Configuration
	@ConditionalOnBean(FrameworkEndpointHandlerMapping.class)
	class SpringSecurityOAuth2ProviderConfiguration {
		@Bean
		public SpringSecurityOAuth2Provider springSecurityOAuth2Provider(FrameworkEndpointHandlerMapping oauth2EndpointHandlerMapping) {
			return new SpringSecurityOAuth2Provider(oauth2EndpointHandlerMapping);
		}
	}
}
