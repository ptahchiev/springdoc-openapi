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

package test.org.springdoc.api

import nonapi.io.github.classgraph.utils.FileUtils
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.springdoc.core.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

@WebFluxTest
@ActiveProfiles("test")
abstract class AbstractKotlinSpringDocTest {

	@Autowired
	private val webTestClient: WebTestClient? = null


	@Test
	@Throws(Exception::class)
	fun testApp() {
		val getResult = webTestClient!!.get().uri(Constants.DEFAULT_API_DOCS_URL).exchange()
				.expectStatus().isOk.expectBody().returnResult()

		val result = String(getResult.responseBody!!)
		val className = javaClass.simpleName
		val testNumber = className.replace("[^0-9]".toRegex(), "")

		val expected = getContent("results/app$testNumber.json")
		JSONAssert.assertEquals(expected, result, true)
	}

	companion object {

		@Throws(Exception::class)
		fun getContent(fileName: String): String {
			try {
				val path = Paths.get(FileUtils::class.java.classLoader.getResource(fileName)!!.toURI())
				val fileBytes = Files.readAllBytes(path)
				return String(fileBytes, StandardCharsets.UTF_8)
			} catch (e: Exception) {
				throw RuntimeException("Failed to read file: $fileName", e)
			}

		}
	}
}