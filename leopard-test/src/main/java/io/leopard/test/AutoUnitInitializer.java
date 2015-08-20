package io.leopard.test;

import io.leopard.autounit.config.AutoUnitConfigImpl;

import java.io.IOException;
import java.util.List;

public class AutoUnitInitializer {

	public static void init() throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {
		List<String> classNameList = new AutoUnitConfigImpl().listIntegrationRunnable();

		for (String className : classNameList) {
			@SuppressWarnings("unchecked")
			Class<Runnable> clazz = (Class<Runnable>) Class.forName(className);
			clazz.newInstance().run();
		}
	}
}
