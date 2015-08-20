package io.leopard.test;

import java.util.List;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class IntegrationRunner extends SpringJUnit4ClassRunner {

	public IntegrationRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

	}

	@Override
	protected void collectInitializationErrors(List<Throwable> errors) {
		super.collectInitializationErrors(errors);

		// TestClass testClass = super.getTestClass();
		// NoLog anno = testClass.getJavaClass().getAnnotation(NoLog.class);
		//
		// if (anno == null) {
		// return;
		// }
		//
		// String config = "log4j.rootLogger=INFO, stdout\n";
		// config += "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n";
		// config += "log4j.appender.stdout.Threshold=FATAL\n";
		// config += "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n";
		// config += "log4j.appender.stdout.layout.ConversionPattern=%d %p [%x,%t] - [%c] - %m%n\n";
		//
		// InputStream input = new ByteArrayInputStream(config.getBytes());
		// PropertyConfigurator.configure(input);
	}

	@Override
	protected void validateTestMethods(List<Throwable> errors) {
		// 忽略测试代码合法性检查
		// super.validateTestMethods(errors);
	}

	// @Override
	// protected Statement methodInvoker(FrameworkMethod method, Object test) {
	// return new SmartParameterInvokeMethod(method, test);
	// }

}
