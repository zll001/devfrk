/**
 * Copyright (2018, ) Hebei Turing CO., LTD.
 */
package com.github.qcase.webfrk;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.qcase.webfrk.core.HttpConstants;

/**
 * @author wuheng(@otcaix.iscas.ac.cn)
 * @date 2018年4月11日
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.github.qcase.webfrk.core.HttpController.class)
@AutoConfigureMockMvc
public class RequestControllerTest  {

	public final static String ROOT_PATH = "/";
	
	public final static String VALID_PATH = "/createExample";
	
	public final static String INVALID_PATH = "/abc/abc";
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void testNullRequestBody() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(
							ROOT_PATH).accept(MediaType.APPLICATION_JSON);
		mvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("status").value(HttpConstants.HTTP_RESPONSE_STATUS_FAILED));
	}
	
	@Test
	public void testInvalidRequestBody() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(
							INVALID_PATH).accept(MediaType.APPLICATION_JSON);
		mvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("status").value(HttpConstants.HTTP_RESPONSE_STATUS_FAILED))
				.andExpect(jsonPath("message").value(HttpConstants.EXCEPTION_INVALID_REQUEST_URL));
	}
	
}
