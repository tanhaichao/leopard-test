package io.leopard.jetty.impl;

import io.leopard.jetty.ServerInitializer;

public class ServerInitializerImpl implements ServerInitializer {

	@Override
	public void run() {
		String className = "io.leopard.javahost.AutoUnitRunnable";
		try {
			Runnable runnable = (Runnable) Class.forName(className).newInstance();
			runnable.run();
		}
		catch (Exception e) {
			// System.err.println("init hosts error:" + e.toString());
			// e.printStackTrace();
		}
		System.setProperty("spring.profiles.active", "dev");

	}

}
