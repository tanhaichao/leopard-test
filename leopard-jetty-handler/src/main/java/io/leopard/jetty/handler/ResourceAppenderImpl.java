package io.leopard.jetty.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ResourceAppenderImpl implements ResourceAppender {

	private List<ResourceAppender> list = new ArrayList<ResourceAppender>();

	@Override
	public void append(HttpServletRequest request, String path, StringBuffer sb) {
		for (ResourceAppender appender : list) {

			appender.append(request, path, sb);
		}
		sb.append("\n\n" + "//Hello APITest");
	}

}
