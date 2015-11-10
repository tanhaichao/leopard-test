package io.leopard.jetty.test;

import io.leopard.httpnb.Httpnb;
import io.leopard.jetty.JettyServer;

import org.eclipse.jetty.server.Server;

public class JettyHttpnb {

	public static String doGet(String url) {
		Server server;
		try {
			server = JettyServer.start("src/test/webapp");
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		String result = Httpnb.doGet(url);
		try {
			server.stop();
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}
}
