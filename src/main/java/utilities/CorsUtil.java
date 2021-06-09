package utilities;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsUtil implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse,
			FilterChain corsFilterChain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) httpServletRequest;

		((HttpServletResponse) httpServletResponse).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) httpServletResponse).addHeader("Access-Control-Allow-Methods", "GET, DELETE, PUT, POST");
		((HttpServletResponse) httpServletResponse).addHeader("Access-Control-Allow-Headers", "content-type");

		HttpServletResponse resp = (HttpServletResponse) httpServletResponse;

		if (request.getMethod().equals("OPTIONS")) {
			resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			return;
		}

		corsFilterChain.doFilter(request, httpServletResponse);
	}

	@Override
	public void destroy() {

	}

}
