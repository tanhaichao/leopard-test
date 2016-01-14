package io.leopard.test.mvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public interface XParamBuilder {

	void param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value);
}
