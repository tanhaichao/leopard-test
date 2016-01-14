package io.leopard.test.mvc;

import java.lang.reflect.Type;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class XParamBuilderSessionIdImpl implements XParamBuilder {

	@Override
	public boolean param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Type type) {
		requestBuilder.cookie(new Cookie("sessionId", (String) value));
		return true;
	}

}
