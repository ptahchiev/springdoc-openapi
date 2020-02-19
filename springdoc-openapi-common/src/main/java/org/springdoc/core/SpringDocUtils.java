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

import io.swagger.v3.oas.models.media.Schema;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.core.converters.AdditionalModelsConverter;
import org.springdoc.core.converters.ConverterUtils;

public class SpringDocUtils {

	private static final SpringDocUtils springDocConfig = new SpringDocUtils();

	private SpringDocUtils() { }

	public static SpringDocUtils getConfig() {
		return springDocConfig;
	}

	public SpringDocUtils addDeprecatedType(Class<?> cls) {
		AbstractOpenApiResource.addDeprecatedType(cls);
		return this;
	}

	public SpringDocUtils addRestControllers(Class<?>... classes) {
		AbstractOpenApiResource.addRestControllers(classes);
		return this;
	}

	public SpringDocUtils addHiddenRestControllers(Class<?>... classes) {
		AbstractOpenApiResource.addHiddenRestControllers(classes);
		return this;
	}

	public SpringDocUtils replaceWithClass(Class source, Class target) {
		AdditionalModelsConverter.replaceWithClass( source,  target);
		return this;
	}

	public SpringDocUtils replaceWithSchema(Class source, Schema target) {
		AdditionalModelsConverter.replaceWithSchema( source,  target);
		return this;
	}

	public SpringDocUtils addRequestWrapperToIgnore(Class<?>... classes) {
		AbstractRequestBuilder.addRequestWrapperToIgnore( classes);
		return this;
	}

	public SpringDocUtils removeRequestWrapperToIgnore(Class<?>... classes) {
		AbstractRequestBuilder.removeRequestWrapperToIgnore( classes);
		return this;
	}

	public SpringDocUtils addFileType(Class<?>... classes) {
		GenericParameterBuilder.addFileType(classes);
		return this;
	}

	public SpringDocUtils addResponseWrapperToIgnore(Class<?> cls){
		ConverterUtils.addResponseWrapperToIgnore(cls);
		return this;
	}

	public SpringDocUtils addResponseTypeToIgnore(Class<?> cls){
		ConverterUtils.addResponseTypeToIgnore(cls);
		return this;
	}

}

