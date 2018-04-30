package br.com.superpestana.springbootRest.auth;

import java.io.IOException;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

public class AuthFilter extends GenericFilterBean {
	
  
    private JwtService jwtService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
        if(jwtService==null){
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            jwtService = webApplicationContext.getBean(JwtService.class);
        }
		
		HttpServletRequest req = (HttpServletRequest) request;
		try {
			jwtService.validateToken(req);
		} catch (AuthException e) {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            return ;
		}
		chain.doFilter(request, response);
	}




}
