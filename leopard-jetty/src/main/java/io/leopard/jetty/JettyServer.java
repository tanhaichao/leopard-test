package io.leopard.jetty;

import io.leopard.jetty.impl.WebServerJettyImpl;

import org.eclipse.jetty.server.Server;

/**
 * 适用于servlet3.0+jetty8，本机开发使用。
 */
public class JettyServer {

	public static Server start() throws Exception {
		return start(80);
	}

	public static Server start(int port) throws Exception {
		Server server = start("/", port);
		return server;
	}

	public static Server start(String contextPath, int port) throws Exception {

		Server server = new WebServerJettyImpl().build(port, contextPath);
		server.start();
		return server;
	}

}
