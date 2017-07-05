/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suncd;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.notNullValue;

import javax.servlet.RequestDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoRestdocsApplicationTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.build();
	}

	@Test
	public void test() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("indexTest"));
	}

	@Test
	public void errorExample() throws Exception {
		this.mockMvc
				.perform(get("/error")
						.requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400)
						.requestAttr(RequestDispatcher.ERROR_REQUEST_URI,
								"/notes")
						.requestAttr(RequestDispatcher.ERROR_MESSAGE,
								"The tag 'http://localhost:8080/tags/123' does not exist"))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("error", is("Bad Request")))
				.andExpect(jsonPath("timestamp", is(notNullValue())))
				.andExpect(jsonPath("status", is(400)))
				.andExpect(jsonPath("path", is(notNullValue())))
				.andDo(document("error-example",
						responseFields(
								fieldWithPath("error").description("The HTTP error that occurred, e.g. `Bad Request`"),
								fieldWithPath("message").description("A description of the cause of the error"),
								fieldWithPath("path").description("The path to which the request was made"),
								fieldWithPath("status").description("The HTTP status code, e.g. `400`"),
								fieldWithPath("timestamp").description("The time, in milliseconds, at which the error occurred"))));
	}

	@Test
	public void getUser() throws Exception{
		this.mockMvc.perform(get("/user/5").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(document("index",
						preprocessResponse(prettyPrint()),
						responseFields(
								fieldWithPath("email")	.description("The user's email address"),
								fieldWithPath("name").description("The user's name")
						)
				));
	}

	@Test
	public void getStr() throws Exception{
		this.mockMvc.perform(get("/user/str").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andDo(document("userStr"//,
//						relaxedResponseFields(
//								fieldWithPath("ddd").description("return String!")
//						)
				));
	}

	@Test
	public void getTest() throws Exception{
		this.mockMvc.perform(get("/user/{id}", "mc").param("title","mc").param("title1","mcc"))
				.andExpect(status().isOk())
				.andDo(document("locations",
						pathParameters(
								parameterWithName("id").description("The location's latitude")
						),
						requestParameters(
								parameterWithName("title").description("The user's username"),
								parameterWithName("title1").description("The user's username2")
						),
						responseFields(
								fieldWithPath("email")	.description("The user's email address"),
								fieldWithPath("name").description("The user's name")
						)
				));
	}

	@Test
	public void testListMap() throws Exception {
		MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
		map.add("arg1","1213");
		map.add("arg2","2222");
		this.mockMvc.perform(
				get("/test").params(map)/*1.请求url及对应path参数赋值*/
						.header("access_token", "b1ec9c03-36ce-4e9b-8b23-e26406623e0a")/*2.设置请求头参数及赋值--固定*/
						.accept(MediaType.APPLICATION_JSON)/*4.设置accept*/
		).andExpect(status().isOk())
				.andDo(
						document(
								"map", /*5.输出目录的名称--自定义*/
								preprocessResponse(prettyPrint()),/*6.响应的数据进行JSON格式美化--固定*/
								requestHeaders(/*7.请求头参数描述--固定*/
										headerWithName("access_token").description("access_token授权码")
								),
//                                pathParameters(/*8.path参数描述--name必须一致*/
//                                        parameterWithName("id1").description("第一个path参数说明"),
//                                        parameterWithName("id2").description("第二个path参数说明")
//                                ),
								requestParameters(/*9.requestParam参数描述--name必须一致*/
										parameterWithName("arg1").description("第一个RequestParam参数说明 #(必填)#"),
										parameterWithName("arg2").description("第二个RequestParam参数说明 #(选填)#")
								),
								responseFields(/*10.响应参数描述--path必须一致*/
										fieldWithPath("[]").description("Array对象"),
										fieldWithPath("[].arg1").description("arg1注释说明"),
										fieldWithPath("[].arg2").description("arg2注释说明")


								)
						)
				);
	}

	@After
	public void tearDown() {

	}
}
