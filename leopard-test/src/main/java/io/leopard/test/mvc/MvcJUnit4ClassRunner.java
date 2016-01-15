package io.leopard.test.mvc;

import java.util.Date;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

public class MvcJUnit4ClassRunner extends SpringJUnit4ClassRunner {

	public MvcJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
		System.out.println("start:" + new Date());
	}

}
