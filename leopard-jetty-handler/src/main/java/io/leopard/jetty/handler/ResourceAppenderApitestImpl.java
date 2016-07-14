package io.leopard.jetty.handler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;

public class ResourceAppenderApitestImpl implements ResourceAppender {

	private static Set<String> HOST_SET = new HashSet<String>();
	static {
		HOST_SET.add("fshop.leopard.io");
		HOST_SET.add("ftrade.leopard.io");

		HOST_SET.add("fshop.test.leopard.io");
		HOST_SET.add("ftrade.test.leopard.io");
	}

	@Override
	public void append(HttpServletRequest request, String path, StringBuffer sb) {
		if (!"/js/jquery.min.js".equals(path)) {
			return;
		}
		String serverName = request.getServerName();
		if (!HOST_SET.contains(serverName)) {
			return;
		}
		Set<String> servletSet = this.getServlets(request);
		if (!servletSet.contains("/apitest/index.leo")) {
			return;
		}
		sb.append("\n\nHello APITest...");
	}

	protected Set<String> getServlets(HttpServletRequest request) {
		Map<String, ? extends ServletRegistration> map = request.getServletContext().getServletRegistrations();
		Set<String> servletSet = new HashSet<String>();
		for (Entry<String, ? extends ServletRegistration> entry : map.entrySet()) {
			// String name = entry.getKey();
			ServletRegistration servlet = entry.getValue();
			servletSet.addAll(servlet.getMappings());
			// System.out.println("name:" + name + " url:" + servlet.getMappings());
		}
		return servletSet;
	}

}
