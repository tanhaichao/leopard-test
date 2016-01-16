package io.leopard.test.mvc;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.leopard.test.UseH2;

public class MvcJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	public MvcJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);

		UseH2 anno = clazz.getAnnotation(UseH2.class);
		boolean useH2 = true;
		if (anno != null) {
			useH2 = anno.value();
		}
		System.setProperty("useH2", Boolean.toString(useH2));
		System.out.println("MvcJUnit4ClassRunner useH2:" + Boolean.toString(useH2));
	}

}
