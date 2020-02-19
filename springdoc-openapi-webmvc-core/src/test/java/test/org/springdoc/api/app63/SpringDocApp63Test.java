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

package test.org.springdoc.api.app63;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import test.org.springdoc.api.AbstractSpringDocTest;

import org.springframework.boot.autoconfigure.SpringBootApplication;

public class SpringDocApp63Test extends AbstractSpringDocTest {

	@BeforeAll
	public static void beforeClass() {
		System.setProperty("springdoc.packagesToScan", "hell,hello1, hello.me");
		System.setProperty("springdoc.packagesToExclude", "test.org.springdoc.api.app63.65");
	}

	@AfterAll
	public static void afterClass() {
		System.clearProperty("springdoc.packagesToScan");
		System.clearProperty("springdoc.packagesToExclude");
	}

	@SpringBootApplication
	static class SpringDocTestApp {}
}
