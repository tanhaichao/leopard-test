package io.leopard.jetty.handler;

import javax.servlet.http.HttpServletRequest;

public interface ResourceAppender {

	String append(HttpServletRequest request, String path, String content);

}
