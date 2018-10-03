/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.restassured.module.webtestclient;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.module.webtestclient.setup.ParserController;
import io.restassured.parsing.Parser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.web.util.UriUtils;

import static org.apache.commons.codec.Charsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;

public class ParserTest {

	@Before
	public void
	given_controller_is_assigned_to_rest_assured() {
		RestAssuredWebTestClient.standaloneSetup(new ParserController());
	}

	@After
	public void
	rest_assured_is_reset() throws Exception {
		RestAssuredWebTestClient.reset();
	}

	@Test
	public void
	using_static_parser_its_possible_to_parse_unknown_content_types() {
		RestAssuredWebTestClient.responseSpecification = new ResponseSpecBuilder()
				.registerParser("some/thing", Parser.JSON).build();

		RestAssuredWebTestClient.given().
				param("param", "my param").
				when().
				get("/parserWithUnknownContentType").
				then().
				statusCode(200).
				contentType(equalTo("some/thing;charset=UTF-8")).
				body("param", equalTo(UriUtils.encode("my param", UTF_8)));
	}

	@Test
	public void
	using_non_static_parser_its_possible_to_parse_unknown_content_types() {
		RestAssuredWebTestClient.given().
				param("param", "my param").
				when().
				get("/parserWithUnknownContentType").
				then().
				parser("some/thing", Parser.JSON).
				statusCode(200).
				contentType(equalTo("some/thing;charset=UTF-8")).
				body("param", equalTo(UriUtils.encode("my param", UTF_8)));
	}
}
