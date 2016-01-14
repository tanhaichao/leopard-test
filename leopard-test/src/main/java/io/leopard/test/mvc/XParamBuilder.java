package io.leopard.test.mvc;

import java.lang.reflect.Type;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public interface XParamBuilder {

	boolean param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Type type);
}
