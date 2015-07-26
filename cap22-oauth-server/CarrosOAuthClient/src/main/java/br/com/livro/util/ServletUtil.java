package br.com.livro.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rlecheta
 *
 */
public class ServletUtil {

	public static String getRequestURL(HttpServletRequest req) {
		StringBuffer sb = req.getRequestURL();
		String queryString = req.getQueryString();
		if (queryString != null) {
			sb.append("?").append(queryString);
		}
		String url = sb.toString();
		return url;
	}
}
