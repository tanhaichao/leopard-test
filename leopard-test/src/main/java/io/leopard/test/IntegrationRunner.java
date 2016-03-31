package io.leopard.test;

import java.util.List;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class IntegrationRunner extends SpringJUnit4ClassRunner {

	public IntegrationRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		// TODO
		// UseH2 anno = clazz.getAnnotation(UseH2.class);
		// if (anno != null) {
		// boolean useH2 = anno.value();
		// boolean rollback = anno.rollback();
		// DefaultH2DataSource.setUseH2(useH2, "integration", !rollback);
		// }
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
