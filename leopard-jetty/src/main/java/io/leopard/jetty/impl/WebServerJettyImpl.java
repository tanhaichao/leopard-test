package io.leopard.jetty.impl;

import io.leopard.jetty.ServerInitializer;
import io.leopard.jetty.configuration.EmbedWebInfConfiguration;

import java.io.IOException;
import java.net.BindException;

import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

public class WebServerJettyImpl extends AbstractWebServer {

	private ServerInitializer serverInitializer = new ServerInitializerImpl();

	/**
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	@Override
	public Server build(int port, String webApp, String contextPath) throws BindException {
		port = this.getAutoPort(port);

		serverInitializer.run();

		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext(webApp, contextPath);
		webContext.setDefaultsDescriptor("leopard-jetty/webdefault.xml");

		// 问题点：http://stackoverflow.com/questions/13222071/spring-3-1-webapplicationinitializer-embedded-jetty-8-annotationconfiguration
		webContext.setConfigurations(new Configuration[] { //
				new EmbedWebInfConfiguration()//
						, new WebInfConfiguration()//
						, new MetaInfConfiguration()//
						, new FragmentConfiguration()//
						, new AnnotationConfiguration() //
						, new WebXmlConfiguration() //
				// new PlusConfiguration(),
				// new EnvConfiguration()
				});

		WebAppClassLoader classLoader = null;
		try {
			// addTldLib(webContext);
			classLoader = new WebAppClassLoader(webContext);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		// ClassLoader tldClassLoader = addTldLib(classLoader);
		webContext.setClassLoader(classLoader);

		webContext.setParentLoaderPriority(true);
		// logger.debug(webContext.dump());
		server.setHandler(webContext);
		server.setStopAtShutdown(true);

		return server;
	}

}
