package io.leopard.test.mvc;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class XParamBuilderImpl implements XParamBuilder {

	private XParamBuilderPassportImpl xParamBuilderPassportImpl;

	private XParamBuilderSessionIdImpl xParamBuilderSessionIdImpl = new XParamBuilderSessionIdImpl();

	public XParamBuilderImpl(MockHttpServletRequestBuilder requestBuilder, String[] names, Object[] args, List<Cookie> cookieList) {
		xParamBuilderPassportImpl = new XParamBuilderPassportImpl(cookieList);

		for (int i = 0; i < args.length; i++) {
			String name = names[i];
			Object value = args[i];
			this.param(requestBuilder, i, name, value);
		}
	}

	@Override
	public void param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value) {
		if ("sessUid".equals(name) || "sessUsername".equals(name) || "sessPassport".equals(name)) {
			xParamBuilderPassportImpl.param(requestBuilder, index, name, value);
		}
		else if ("sessionId".equals(name)) {
			xParamBuilderSessionIdImpl.param(requestBuilder, index, name, value);
		}
	}

}
