package io.leopard.test;

import java.util.HashSet;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;

/**
 * 方法调用堆栈.
 * 
 * @author 阿海
 *
 */
public class TestAopInterceptor extends BeanNameAutoProxyCreator implements MethodInterceptor {

	private static final long serialVersionUID = 1L;

	public TestAopInterceptor() {

		String[] beanNames = new String[] { "*Controller", "*Service"//
				, "*Dao*"//
				, "*Queue"//
		};
		this.setBeanNames(beanNames);

		this.setProxyTargetClass(true);
		this.setInterceptorNames(new String[] { "testAopInterceptor" });
	}

	private static Set<String> IGNORED_BEAN_NAME_SET = new HashSet<String>();

	static {
		IGNORED_BEAN_NAME_SET.add("conversionService");
	}

	@Override
	protected boolean isMatch(String beanName, String mappedName) {

		if (IGNORED_BEAN_NAME_SET.contains(beanName)) {
			return false;
		}

		return super.isMatch(beanName, mappedName);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {

		String className = invocation.getThis().getClass().getName();
		logger.info("TestAopInterceptor invoke className:" + className + " method:" + invocation.getMethod().toGenericString());
		Object result = invocation.proceed();
		return result;
	}

}
