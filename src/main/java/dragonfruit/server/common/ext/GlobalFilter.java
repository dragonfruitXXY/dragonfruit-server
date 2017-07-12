package dragonfruit.server.common.ext;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import xuyihao.i18n.I18nContext;

/**
 * 全局拦截器
 * 
 * Created by xuyh at 2017年7月12日 下午4:34:22.
 */
public class GlobalFilter implements Filter {
	private static final String COOKIE_NAME_LANGUAGE = "language";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//获取前端传递的语言信息，从cookie中获取
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpServletRequest.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(COOKIE_NAME_LANGUAGE)) {
				String lang = cookie.getValue();
				I18nContext.setLanguage(lang);
				break;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
