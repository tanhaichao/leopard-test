package io.leopard.test.mvc;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import io.leopard.burrow.lang.inum.Snum;

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

		name = camelToUnderline(name);

		if (typeName.equals(String.class.getName())) {
			// System.err.println("requestBuilder param name:" + name + " value:" + value);
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

		else if (typeName.equals(MultipartFile.class.getName())) {
			MultipartFile file = (MultipartFile) value;
			try {
				((MockMultipartHttpServletRequestBuilder) requestBuilder).file(name, file.getBytes());
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		else {
			Class<?> clazz;
			try {
				clazz = Class.forName(typeName);
			}
			catch (ClassNotFoundException e) {
				throw new RuntimeException(e.getMessage(), e);
			}

			if (clazz.isEnum()) {
				if (clazz.isAssignableFrom(Snum.class)) {
					String key = ((Snum) value).getKey();
					requestBuilder.param(name, key);
				}
				else {
					throw new IllegalArgumentException("未知枚举类型[" + typeName + "].");
				}
			}
			else {
				throw new IllegalArgumentException("未知类型[" + typeName + "].");
			}
		}
		return true;
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线方式.
	 */
	public static String camelToUnderline(String param) {
		if (param == null || param.length() == 0) {
			return param;
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_');
				sb.append(Character.toLowerCase(c));
			}
			else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
