package io.leopard.test.mvc;

import java.lang.reflect.Method;
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
		return perform(clazz, null);
	}

	public static <T> T perform(Class<T> clazz, List<Cookie> cookieList) {

		T bean;
		try {
			bean = clazz.newInstance();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		T proxy = ClassProxy.newProxyInstance(clazz, new MethodHandlerImpl(bean));
		return proxy;
	}

	private static class MethodHandlerImpl implements MethodHandler {

		private final Object bean;

		public MethodHandlerImpl(final Object bean) {
			this.bean = bean;
		}

		@Override
		public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
			// String[] names = CtClassUtil.getParameterNames(thisMethod);

			// String json = (String) thisMethod.invoke(bean, args);

			String uri = this.getUri(thisMethod);

			String methodName = thisMethod.getName();
			System.out.println("methodName:" + methodName + " uri:" + uri);
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			String json = result.getResponse().getContentAsString();

			Map<String, Object> map = Json.toMap(json);
			String status = (String) map.get("status");
			String message = (String) map.get("message");
			Object data = map.get("data");
			if (!"success".equals(status)) {
				throw new RuntimeException(message + "(" + status + ")");
			}

			System.out.println("uri:" + uri + " json:" + json);
			return data;
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
