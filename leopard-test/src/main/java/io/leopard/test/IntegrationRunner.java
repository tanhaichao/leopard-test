package io.leopard.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

public class IntegrationRunner extends SpringJUnit4ClassRunner {

	private Class<?> clazz;

	public IntegrationRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		this.clazz = clazz;
		// TODO
		// UseH2 anno = clazz.getAnnotation(UseH2.class);
		// if (anno != null) {
		// boolean useH2 = anno.value();
		// boolean rollback = anno.rollback();
		// DefaultH2DataSource.setUseH2(useH2, "integration", !rollback);
		// }
	}

	protected boolean isOnlyTransactional() throws IOException {
		InputStream input = clazz.getResourceAsStream("/autounit.properties");
		Properties props = new Properties();
		props.load(input);
		input.close();
		String transactional = (String) props.get("transactional");
		return "true".equals(transactional);
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		boolean isOnlyTransactional;
		try {
			isOnlyTransactional = isOnlyTransactional();
			System.err.println("isOnlyTransactional:" + isOnlyTransactional);
		}
		catch (IOException e) {
			isOnlyTransactional = false;
		}

		if (!isOnlyTransactional) {
			super.runChild(method, notifier);
		}

		Description description = describeChild(method);
		if (method.getAnnotation(Transactional.class) == null) {
			notifier.fireTestIgnored(description);
		}
		else {
			super.runChild(method, notifier);
		}
	}

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		super.collectInitializationErrors(errors);

	}

	@Override
	protected void validateTestMethods(List<Throwable> errors) {
		// 忽略测试代码合法性检查
		// super.validateTestMethods(errors);
	}

}
