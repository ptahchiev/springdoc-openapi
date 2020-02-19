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

package org.springdoc.core.converters;

import java.util.Collection;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.type.CollectionType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

/**
 * Override resolved schema as there is a custom serializer that converts the data to a map before serializing it.
 *
 * @see org.springframework.hateoas.mediatype.hal.Jackson2HalModule.HalResourcesSerializer
 * @see org.springframework.hateoas.mediatype.hal.Jackson2HalModule.HalResourcesSerializer#serialize(Collection, JsonGenerator, SerializerProvider)
 */
public class CollectionModelContentConverter implements ModelConverter {

	private static final CollectionModelContentConverter collectionModelContentConverter = new CollectionModelContentConverter();

	private CollectionModelContentConverter() { }

	public static CollectionModelContentConverter getConverter() {
		return collectionModelContentConverter;
	}

    @Override
    public Schema<?> resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (chain.hasNext() && type != null && type.getType() instanceof CollectionType
                && "_embedded".equalsIgnoreCase(type.getPropertyName())) {
            Schema<?> schema = chain.next().resolve(type, context, chain);
            if (schema instanceof ArraySchema) {
                return new MapSchema()
                        .name("_embedded")
                        .additionalProperties(new StringSchema())
                        .additionalProperties(schema);
            }
        }
        return chain.hasNext() ? chain.next().resolve(type, context, chain) : null;
    }
}
