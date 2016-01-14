package io.leopard.test.mvc;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.leopard.javahost.AutoUnitRunnable;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextHierarchy({ @ContextConfiguration(name = "parent", locations = "classpath:/leopard-web/applicationContext.xml") })
public class MockMvcTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	protected void example() throws Exception {
		new AutoUnitRunnable().run();

		// Cookie: SESSIONID=347ab4e7-2483-4aba-b365-e9a6ca27db9f; JSESSIONID=jgrc0g2jjti979tznpl6dpm; passport=13924718422; uid=2540138; token=NTZjZmFmZTRkMTFjMjQ2NDIwZGQ0MDQ0ZGIyYjMwYWVhOTZiODA1NDoxNDUyNzk3MjIyMjk4
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/welcome.do");
		requestBuilder.cookie(new Cookie("SESSIONID", "347ab4e7-2483-4aba-b365-e9a6ca27db9f"));
		requestBuilder.cookie(new Cookie("JSESSIONID", "jgrc0g2jjti979tznpl6dpm"));
		requestBuilder.cookie(new Cookie("passport", "13924718422"));
		requestBuilder.cookie(new Cookie("uid", "2540138"));
		requestBuilder.cookie(new Cookie("token", "NTZjZmFmZTRkMTFjMjQ2NDIwZGQ0MDQ0ZGIyYjMwYWVhOTZiODA1NDoxNDUyNzk3MjIyMjk4"));

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("result:" + result);
		System.out.println("result:" + result.getResponse().getContentAsString());
	}
}
