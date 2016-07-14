package io.leopard.jetty.handler;

import javax.servlet.http.HttpServletRequest;

public class ResourceAppenderImpl implements ResourceAppender {

	@Override
	public String append(HttpServletRequest request, String path, String content) {
		return content + "\n\n" + "//Hello APITest";
	}

}
