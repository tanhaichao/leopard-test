package io.leopard.test.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;

import io.leopard.json.Json;
import javassist.util.proxy.MethodHandler;

public class MvcTester {

	private static MockMvc mockMvc;

	public static MockMvc getMockMvc() {
		return mockMvc;
	}

	public static void setMockMvc(MockMvc mockMvc) {
		MvcTester.mockMvc = mockMvc;
	}

	public static <T> T controller(Class<T> clazz) {
		return controller(clazz, null);
	}

	public static <T> T controller(Class<T> clazz, List<Cookie> cookieList) {
		// T bean;
		// try {
		// bean = clazz.newInstance();
		// }
		// catch (Exception e) {
		// throw new RuntimeException(e.getMessage(), e);
		// }
		T proxy = ClassProxy.newProxyInstance(clazz, new MethodHandlerImpl(cookieList));
		return proxy;
	}

	private static class MethodHandlerImpl implements MethodHandler {

		// protected final Object bean;
		protected List<Cookie> cookieList;

		public MethodHandlerImpl(List<Cookie> cookieList) {
			this.cookieList = cookieList;
		}

		@Override
		public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
			if (thisMethod.getName().equals("toString") || thisMethod.getName().equals("hashCode")) {
				return proceed.invoke(self, args);
			}

			String uri = this.getUri(thisMethod);
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

			if (cookieList == null || cookieList.isEmpty()) {
				System.err.println("thisMethod:" + thisMethod.toGenericString());
				System.err.println("proceed:" + proceed.toGenericString());
				cookieList = LoginCookieImpl.getCookies(thisMethod, args);
			}

			if (cookieList != null) {
				for (Cookie cookie : cookieList) {
					requestBuilder.cookie(cookie);
				}
			}

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			String json = result.getResponse().getContentAsString();

			Map<String, Object> map = Json.toMap(json);
			String status = (String) map.get("status");
			Object data = map.get("data");
			System.out.println("uri:" + uri + " json:" + json);
			if ("success".equals(status)) {
				return data;
			}

			String message = (String) map.get("message");
			String exceptionClassName = (String) map.get("exception");

			Exception e = this.toException(exceptionClassName, message);
			// e.printStackTrace();
			throw e;
		}

		protected Exception toException(String className, String message) throws Throwable {
			Class<?> clazz = Class.forName(className);

			Constructor<?> constructor = clazz.getConstructors()[0];
			Class<?>[] types = constructor.getParameterTypes();
			Object[] initargs = new Object[types.length];
			for (int i = 0; i < types.length; i++) {
				initargs[i] = getDefaultValue(types[i]);
			}
			Exception exception = (Exception) constructor.newInstance(initargs);

			Field field = Throwable.class.getDeclaredField("detailMessage");
			field.setAccessible(true);
			field.set(exception, message);
			return exception;
		}

		protected Object getDefaultValue(Class<?> type) {
			if (type.equals(String.class)) {
				return "str";
			}
			else if (type.equals(int.class) || type.equals(Integer.class)) {
				return 1;
			}
			else if (type.equals(long.class) || type.equals(Long.class)) {
				return 1L;
			}
			else if (type.equals(float.class) || type.equals(Float.class)) {
				return 1f;
			}
			else if (type.equals(double.class) || type.equals(Double.class)) {
				return 1d;
			}
			else if (type.equals(Date.class)) {
				return new Date();
			}
			throw new IllegalArgumentException("未知参数类型[" + type.getName() + "].");
		}

		protected String getUri(Method thisMethod) {
			String baseUri;
			{
				RequestMapping requestMapping = thisMethod.getDeclaringClass().getDeclaredAnnotation(RequestMapping.class);
				if (requestMapping == null) {
					baseUri = "";
				}
				else {
					baseUri = requestMapping.value()[0];
				}
			}

			RequestMapping requestMapping = thisMethod.getDeclaredAnnotation(RequestMapping.class);
			String uri;
			if (requestMapping == null) {
				uri = baseUri + "/" + thisMethod.getName() + ".do";
			}
			else {
				String[] values = requestMapping.value();
				if (values.length == 0) {
					uri = baseUri + "/" + thisMethod.getName() + ".do";
				}
				else {
					uri = baseUri + values[0];
				}
			}
			uri = uri.replace("//", "/");
			return uri;
		}

	}
}
