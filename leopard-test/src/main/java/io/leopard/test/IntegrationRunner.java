package io.leopard.test;

import java.util.List;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.leopard.jdbc.UseH2;
import io.leopard.jdbc.test.DefaultH2DataSource;

public class IntegrationRunner extends SpringJUnit4ClassRunner {

	public IntegrationRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		UseH2 anno = clazz.getAnnotation(UseH2.class);
		if (anno != null) {
			boolean useH2 = anno.value();
			boolean rollback = anno.rollback();
			// System.setProperty("useH2", Boolean.toString(useH2));
			DefaultH2DataSource.setUseH2(useH2, "integration", !rollback);
			// DefaultH2DataSource.setCategory(autoCommit);
		}
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
