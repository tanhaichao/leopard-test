package io.leopard.test.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;

import io.leopard.autounit.CtClassUtil;

public class LoginCookieImpl implements LoginCookie {

	private static LoginCookie instance = new LoginCookieImpl();

	@Override
	public List<Cookie> getCookies(Long uid) {
		List<Cookie> cookieList = new ArrayList<Cookie>();

		return cookieList;
	}

	@Override
	public List<Cookie> getCookies(String passport) {
		List<Cookie> cookieList = new ArrayList<Cookie>();

		return cookieList;
	}

	public static List<Cookie> getCookies(Method method, Object[] args) {
		String[] names = CtClassUtil.getParameterNames(method);
		Set<String> set = new HashSet<String>();
		for (String name : names) {
			set.add(name);
		}

		{
			int index = Arrays.binarySearch(names, "sessUid");
			if (index >= 0) {
				return instance.getCookies((Long) args[index]);
			}
		}
		{
			int index = Arrays.binarySearch(names, "sessUsername");
			if (index >= 0) {
				return instance.getCookies((String) args[index]);
			}
		}
		{
			int index = Arrays.binarySearch(names, "sessPassport");
			if (index >= 0) {
				return instance.getCookies((String) args[index]);
			}
		}
		return null;
	}
}
