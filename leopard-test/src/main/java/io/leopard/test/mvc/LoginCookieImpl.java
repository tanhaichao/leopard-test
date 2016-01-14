package io.leopard.test.mvc;

import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.BeansException;
import org.springframework.web.context.WebApplicationContext;

public class LoginCookieImpl implements LoginCookie {

	private static final LoginCookie instance = new LoginCookieImpl();

	public static LoginCookie getInstance() {
		return instance;
	}

	private static LoginCookie customLoginCookie = null;

	public static void setApplicationContext(WebApplicationContext wac) {
		try {
			customLoginCookie = wac.getBean(LoginCookie.class);
		}
		catch (BeansException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cookie> getCookies(Long uid) {
		List<Cookie> cookieList = null;
		if (customLoginCookie != null) {
			cookieList = customLoginCookie.getCookies(uid);
		}
		return cookieList;
	}

	@Override
	public List<Cookie> getCookies(String passport) {
		List<Cookie> cookieList = null;
		if (customLoginCookie != null) {
			cookieList = customLoginCookie.getCookies(passport);
		}
		return cookieList;
	}

	// public static List<Cookie> getCookies(Method method, Object[] args) {
	// String[] names = CtClassUtil.getParameterNames(method);
	// Set<String> set = new HashSet<String>();
	// for (String name : names) {
	// set.add(name);
	// }
	//
	// {
	// int index = Arrays.binarySearch(names, "sessUid");
	// if (index >= 0) {
	// return instance.getCookies((Long) args[index]);
	// }
	// }
	// {
	// int index = Arrays.binarySearch(names, "sessUsername");
	// if (index >= 0) {
	// return instance.getCookies((String) args[index]);
	// }
	// }
	// {
	// int index = Arrays.binarySearch(names, "sessPassport");
	// if (index >= 0) {
	// return instance.getCookies((String) args[index]);
	// }
	// }
	// return null;
	// }
}
