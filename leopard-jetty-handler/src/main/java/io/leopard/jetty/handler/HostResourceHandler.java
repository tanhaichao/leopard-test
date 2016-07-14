package io.leopard.jetty.handler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;

public class HostResourceHandler extends ResourceHandler {

	private String host;

	public HostResourceHandler(String resourceBase) {
		this(null, resourceBase);
	}

	public HostResourceHandler(String host, String resourceBase) {
		this.host = host;
		super.setResourceBase(resourceBase);
	}

	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// System.out.println("HostResourceHandler handle serverName:" + request.getServerName() + " host:" + host + " resourceBase:" + this.getResourceBase());
		if (host != null && host.length() > 0) {
			String serverName = request.getServerName();
			// System.out.println("HostResourceHandler serverName:" + serverName + " host:" + host);
			if (!serverName.equals(host)) {
				return;
			}
		}
		super.handle(target, baseRequest, request, response);
		// System.out.println("HostResourceHandler isCommitted:" + response.isCommitted() + " isHandled:" + baseRequest.isHandled() + " target:" + target + " uri:" + request.getRequestURI());

	}

	@Override
	public Resource getResource(String path) {
		Resource resource = super.getResource(path);
		if (resource == null || !resource.exists()) {
			return resource;
		}
		if ("/js/jquery.min.js".equals(path)) {
			try {
				resource = this.append(resource);
			}
			catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return resource;
	}

	protected Resource append(Resource resource) throws IOException {
		InputStream input = resource.getInputStream();
		input.close();
		Resource resource2 = new StringResource("ok");
		// /js/jquery.min.js
		System.err.println("HostResourceHandler getResource:");
		return resource2;
	}

}
