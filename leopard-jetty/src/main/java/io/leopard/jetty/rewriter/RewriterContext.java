package io.leopard.jetty.rewriter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;

public class RewriterContext {

	private static List<Expression> expressionList = new ArrayList<Expression>();

	public static List<Expression> getExpressionList() {
		return expressionList;
	}

	public static Handler getHandler() {
		if (expressionList == null || expressionList.isEmpty()) {
			return null;
		}
		return new RewriterHandler();
	}

	public static void rewrite(String pattern, String file) {

	}

	public static class Expression {
		private String pattern;
		private String file;

		public String getPattern() {
			return pattern;
		}

		public void setPattern(String pattern) {
			this.pattern = pattern;
		}

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}

	}
}
