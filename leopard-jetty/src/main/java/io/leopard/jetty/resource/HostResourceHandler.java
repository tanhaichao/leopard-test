package io.leopard.jetty.resource;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ResourceHandler;

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
		if (host != null && host.length() > 0) {
			String serverName = request.getServerName();
			if (!serverName.equals(host)) {
				return;
			}
		}
		super.handle(target, baseRequest, request, response);
	}

}
