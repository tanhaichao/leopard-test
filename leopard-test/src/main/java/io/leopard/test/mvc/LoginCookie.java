package io.leopard.test.mvc;

import java.util.List;

import javax.servlet.http.Cookie;

public interface LoginCookie {

	List<Cookie> getCookies(Long uid);

	List<Cookie> getCookies(String passport);
}
