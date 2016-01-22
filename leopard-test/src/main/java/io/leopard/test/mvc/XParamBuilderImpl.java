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
import io.leopard.json.Json;

public class XParamBuilderImpl implements XParamBuilder {

	private XParamBuilderPassportImpl xParamBuilderPassportImpl;

	private XParamBuilderSessionIdImpl xParamBuilderSessionIdImpl = new XParamBuilderSessionIdImpl();

	public XParamBuilderImpl(MockHttpServletRequestBuilder requestBuilder, String[] names, Object[] args, Class<?>[] types, Type[] genericTypes, List<Cookie> cookieList) {
		xParamBuilderPassportImpl = new XParamBuilderPassportImpl(cookieList);

		for (int i = 0; i < args.length; i++) {
			this.param(requestBuilder, i, names[i], args[i], types[i], genericTypes[i]);
		}
	}

	@Override
	public boolean param(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Class<?> type, Type genericType) {
		if ("sessUid".equals(name) || "sessUsername".equals(name) || "sessPassport".equals(name)) {
			return xParamBuilderPassportImpl.param(requestBuilder, index, name, value, type, genericType);
		}
		else if ("sessionId".equals(name)) {
			return xParamBuilderSessionIdImpl.param(requestBuilder, index, name, value, type, genericType);
		}
		String typeName = genericType.getTypeName();

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
		else if (value instanceof Snum) {
			requestBuilder.param(name, ((Snum) value).getKey());
		}
		else if (type.equals(List.class)) {
			this.list(requestBuilder, index, typeName, value, type, genericType);
		}
		else {
			throw new IllegalArgumentException("未知类型[" + typeName + "].");
		}
		return true;
	}

	public void list(MockHttpServletRequestBuilder requestBuilder, int index, String name, Object value, Class<?> type, Type genericType) {
		String typeName = genericType.getTypeName();
		if (value == null) {
			return;
		}
		String name2 = name.replace("_list", "");
		if (typeName.equals("java.util.List<java.lang.String>")) {
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) value;
			for (String element : list) {
				requestBuilder.param(name2, element);
			}
		}
		else if (typeName.equals("java.util.List<java.lang.Integer>")) {
			@SuppressWarnings("unchecked")
			List<Integer> list = (List<Integer>) value;
			for (Integer element : list) {
				requestBuilder.param(name2, Integer.toString(element));
			}
		}
		else if (typeName.equals("java.util.List<java.lang.Long>")) {
			@SuppressWarnings("unchecked")
			List<Long> list = (List<Long>) value;
			for (Long element : list) {
				requestBuilder.param(name2, Long.toString(element));
			}
		}
		else {
			String json = Json.toJson(value);
			requestBuilder.param(name2, json);
		}
		// throw new IllegalArgumentException("未知List类型[" + typeName + "].");
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
