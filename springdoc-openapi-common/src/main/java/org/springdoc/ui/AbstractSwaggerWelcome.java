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

package org.springdoc.ui;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigProperties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springdoc.core.Constants.SPRINGDOC_OAUTH2_REDIRECT_URL_VALUE;
import static org.springdoc.core.Constants.SPRINGDOC_SWAGGER_UI_CONFIG_URL_VALUE;
import static org.springdoc.core.Constants.SPRINGDOC_SWAGGER_UI_URL_VALUE;
import static org.springdoc.core.Constants.SWAGGER_UI_OAUTH_REDIRECT_URL;
import static org.springdoc.core.Constants.SWAGGGER_CONFIG_FILE;
import static org.springframework.util.AntPathMatcher.DEFAULT_PATH_SEPARATOR;


public abstract class AbstractSwaggerWelcome implements InitializingBean {

	protected final SwaggerUiConfigProperties swaggerUiConfig;

	protected final SpringDocConfigProperties springDocConfigProperties;

	protected String uiRootPath;

	@Value(SPRINGDOC_SWAGGER_UI_CONFIG_URL_VALUE)
	private String originConfigUrl;

	@Value(SPRINGDOC_OAUTH2_REDIRECT_URL_VALUE)
	private String oauth2RedirectUrl;

	@Value(SPRINGDOC_SWAGGER_UI_URL_VALUE)
	private String swaggerUiUrl;

	public AbstractSwaggerWelcome(SwaggerUiConfigProperties swaggerUiConfig, SpringDocConfigProperties springDocConfigProperties) {
		this.swaggerUiConfig = swaggerUiConfig;
		this.springDocConfigProperties = springDocConfigProperties;
	}

	@Override
	public void afterPropertiesSet() {
		calculateUiRootPath();
	}


	protected void calculateUiRootPath(StringBuilder... sbUrls) {
		StringBuilder sbUrl = new StringBuilder();
		if (ArrayUtils.isNotEmpty(sbUrls))
			sbUrl = sbUrls[0];
		String swaggerPath = swaggerUiConfig.getPath();
		if (swaggerPath.contains(DEFAULT_PATH_SEPARATOR))
			sbUrl.append(swaggerPath, 0, swaggerPath.lastIndexOf(DEFAULT_PATH_SEPARATOR));
		this.uiRootPath = sbUrl.toString();
	}

	protected String buildUrl(String contextPath, final String docsUrl) {
		if (contextPath.endsWith(DEFAULT_PATH_SEPARATOR)) {
			return contextPath.substring(0, contextPath.length() - 1) + docsUrl;
		}
		return contextPath + docsUrl;
	}

	void buildConfigUrl(String contextPath, UriComponentsBuilder uriComponentsBuilder) {
		String apiDocsUrl = springDocConfigProperties.getApiDocs().getPath();
		if (StringUtils.isEmpty(originConfigUrl)) {
			String url = buildUrl(contextPath, apiDocsUrl);
			String swaggerConfigUrl = url + DEFAULT_PATH_SEPARATOR + SWAGGGER_CONFIG_FILE;
			swaggerUiConfig.setConfigUrl(swaggerConfigUrl);
			if (SwaggerUiConfigProperties.getSwaggerUrls().isEmpty()) {
				if (StringUtils.isEmpty(swaggerUiUrl))
					swaggerUiConfig.setUrl(url);
				else
					swaggerUiConfig.setUrl(swaggerUiUrl);
			}
			else
				SwaggerUiConfigProperties.addUrl(url);
		}
		if (StringUtils.isEmpty(oauth2RedirectUrl)) {
			swaggerUiConfig.setOauth2RedirectUrl(uriComponentsBuilder.path(this.uiRootPath).path(SWAGGER_UI_OAUTH_REDIRECT_URL).build().toString());
		}
		else if (!swaggerUiConfig.isValidUrl(swaggerUiConfig.getOauth2RedirectUrl())) {
			swaggerUiConfig.setOauth2RedirectUrl(uriComponentsBuilder.path(this.uiRootPath).path(swaggerUiConfig.getOauth2RedirectUrl()).build().toString());
		}
	}

}