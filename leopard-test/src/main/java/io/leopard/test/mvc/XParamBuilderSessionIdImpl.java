package io.leopard.test.mvc;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class XParamBuilderSessionIdImpl implements XParamBuilder {

	@Override
	public void param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value) {
		requestBuilder.cookie(new Cookie("sessionId", (String) value));
	}

}
