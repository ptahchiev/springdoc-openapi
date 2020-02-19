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

package test.org.springdoc.api.app70.customizer;

import io.swagger.v3.oas.models.parameters.Parameter;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class ParameterCustomizer implements org.springdoc.core.customizers.ParameterCustomizer {
	@Override
	public Parameter customize(Parameter parameterModel, java.lang.reflect.Parameter parameter, HandlerMethod handlerMethod) {
		CustomizedParameter annotation = parameter.getAnnotation(CustomizedParameter.class);
		if (annotation != null) {
			parameterModel.description(parameterModel.getDescription() + ", " + annotation.addition());
		}
		return parameterModel;
	}
}
