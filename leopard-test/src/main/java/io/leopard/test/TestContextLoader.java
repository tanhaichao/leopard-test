package io.leopard.test;

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
		String className = "io.leopard.javahost.AutoUnitRunnable";
		try {
			Runnable runnable = (Runnable) Class.forName(className).newInstance();
			runnable.run();
		}
		catch (Exception e) {
			// System.err.println("init hosts error:" + e.toString());
			// e.printStackTrace();
		}

		if (locations.length == 0) {
			locations = new ApplicationContextLocationImpl().get();
		}
		// files = ArrayUtil.insertFirst(files, "/leopard-test/annotation-config.xml");
		locations = StringUtils.addStringToArray(locations, "/leopard-test/annotation-config.xml");

		return new ClassPathXmlApplicationContext(locations);
	}

}
