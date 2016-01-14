package io.leopard.test.mvc;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class XParamBuilderImpl implements XParamBuilder {

	private XParamBuilderPassportImpl xParamBuilderPassportImpl;

	private XParamBuilderSessionIdImpl xParamBuilderSessionIdImpl = new XParamBuilderSessionIdImpl();

	public XParamBuilderImpl(MockHttpServletRequestBuilder requestBuilder, String[] names, Object[] args, Type[] types, List<Cookie> cookieList) {
		xParamBuilderPassportImpl = new XParamBuilderPassportImpl(cookieList);

		for (int i = 0; i < args.length; i++) {
			this.param(requestBuilder, i, names[i], args[i], types[i]);
		}
	}

	@Override
	public boolean param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Type type) {
		if ("sessUid".equals(name) || "sessUsername".equals(name) || "sessPassport".equals(name)) {
			return xParamBuilderPassportImpl.param(requestBuilder, index, name, value, type);
		}
		else if ("sessionId".equals(name)) {
			return xParamBuilderSessionIdImpl.param(requestBuilder, index, name, value, type);
		}
		String typeName = type.getTypeName();
		
		if (typeName.equals(String.class.getName())) {
			requestBuilder.param(name, (String) value);
		}
		else if (typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())) {
			requestBuilder.param(name, value.toString());
		}
		else if (typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())) {
			requestBuilder.param(name, value.toString());
		}
		else if (typeName.equals(float.class.getName()) || typeName.equals(Float.class.getName())) {
			requestBuilder.param(name, value.toString());
		}
		else if (typeName.equals(double.class.getName()) || typeName.equals(double.class.getName())) {
			requestBuilder.param(name, value.toString());
		}
		else if (typeName.equals(Date.class.getName())) {
			requestBuilder.param(name, ((Date) value).getTime() + "");
		}
		else {
			throw new IllegalArgumentException("未知类型[" + typeName + "].");
		}

		return true;
	}

}
