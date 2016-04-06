package io.leopard.tomcat;

import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

	public static void start() {
		// ResourceLoader resourceLoader = new ClassPathXmlApplicationContext("/leopard-web/applicationContext.xml");
		// SpringApplication app = new SpringApplication(sources);
		// app.setResourceLoader(resourceLoader);
		// app.run();
		Tomcat tomcat = new Tomcat();
		tomcat.setHostname("localhost");
		tomcat.setPort(80);
		tomcat.setBaseDir("D:\\work\\zhongcao\\zhongcao\\zhongcao-web");
	}
}
