package io.leopard.jetty.impl;

import io.leopard.jetty.ServerInitializer;
import io.leopard.jetty.configuration.EmbedAnnotionConfiguration;
import io.leopard.jetty.configuration.EmbedMetaInfConfiguration;
import io.leopard.jetty.configuration.EmbedWebInfConfiguration;
import io.leopard.jetty.configuration.EmbedWebXmlConfiguration;

import java.io.IOException;
import java.net.BindException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class WebServerJettyImpl extends AbstractWebServer {

	private ServerInitializer serverInitializer = new ServerInitializerImpl();

	/**
	 * 创建用于正常运行调试的Jetty Server, 以src/main/webapp为Web应用目录.
	 */
	@Override
	public Server build(int port, String contextPath) throws BindException {
		port = this.getAutoPort(port);

		serverInitializer.run();

		Server server = new Server(port);
		WebAppContext webContext = new WebAppContext("src/main/webapp", contextPath);
		webContext.setDefaultsDescriptor("leopard-jetty/webdefault.xml");

		// 问题点：http://stackoverflow.com/questions/13222071/spring-3-1-webapplicationinitializer-embedded-jetty-8-annotationconfiguration
		webContext.setConfigurations(new Configuration[] { //
				new EmbedWebInfConfiguration()//
						, new EmbedWebXmlConfiguration()//
						, new EmbedMetaInfConfiguration()//
						, new FragmentConfiguration()//
						, new EmbedAnnotionConfiguration() //
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
