package io.leopard.test.mvc;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class XParamBuilderPassportImpl implements XParamBuilder {

	private List<Cookie> cookieList;

	public XParamBuilderPassportImpl(List<Cookie> cookieList) {
		this.cookieList = cookieList;
	}

	@Override
	public boolean param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Type type) {
		List<Cookie> cookieList = this.cookieList;

		if (cookieList == null || cookieList.isEmpty()) {
			if ("sessUid".equals(name)) {
				cookieList = LoginCookieImpl.getInstance().getCookies((Long) value);
			}
			else if ("sessUsername".equals(name)) {
				cookieList = LoginCookieImpl.getInstance().getCookies((String) value);
			}
			else if ("sessPassport".equals(name)) {
				cookieList = LoginCookieImpl.getInstance().getCookies((String) value);
			}
			else {
				throw new IllegalArgumentException("未知参数名称[" + name + "].");
			}
		}

		if (cookieList != null) {
			for (Cookie cookie : cookieList) {
				requestBuilder.cookie(cookie);
			}
		}
		return true;
	}

}
