package io.leopard.test.mvc;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class ClassProxy {

	/**
	 * 模拟从"容器"获取管理的Bean.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(Class<T> clazz, MethodHandler methodHandler) {
		ProxyFactory f = new ProxyFactory();
		f.setSuperclass(clazz);
		f.setFilter(new MethodFilterImpl());
		Class<?> c = f.createClass(); // 创建代理类
		T proxy = null;
		try {
			proxy = (T) c.newInstance(); // 使用代理类创建实例
			// System.out.println("create:" + c.getName());
			((ProxyObject) proxy).setHandler(methodHandler); // 设置方法拦截器
			return proxy;
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected static class MethodFilterImpl implements MethodFilter {
		@Override
		public boolean isHandled(Method method) {
			// System.out.println("isHandled:" + method.toGenericString());
			if (method.getName().equals("finalize")) {
				return false;
			}
			return true;
		}
	}
}
