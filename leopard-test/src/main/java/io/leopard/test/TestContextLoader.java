package io.leopard.test;

import io.leopard.autounit.config.AutoUnitConfigImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.util.StringUtils;

/**
 * 
 * @author 阿海
 * 
 */
public class TestContextLoader implements ContextLoader {

	@Override
	public String[] processLocations(Class<?> clazz, String... locations) {
		return locations;
	}

	@Override
	public ApplicationContext loadContext(String... locations) throws Exception {

		List<String> classNameList;
		try {
			classNameList = new AutoUnitConfigImpl().listIntegrationRunnable();
		}
		catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		for (String className : classNameList) {
			@SuppressWarnings("unchecked")
			Class<Runnable> clazz = (Class<Runnable>) Class.forName(className);
			clazz.newInstance().run();
		}

		if (locations.length == 0) {
			locations = new ApplicationContextLocationImpl().get();
		}
		// files = ArrayUtil.insertFirst(files, "/leopard-test/annotation-config.xml");
		locations = StringUtils.addStringToArray(locations, "/leopard-test/annotation-config.xml");

		return new ClassPathXmlApplicationContext(locations);
	}

}
