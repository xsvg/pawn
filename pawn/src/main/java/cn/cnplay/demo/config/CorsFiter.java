package cn.cnplay.demo.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CorsFiter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "get, post, put, delete, options");
	    response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept");
	    response.setHeader("Access-Control-Allow-Credentials", "true");
	    chain.doFilter(request, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Bean
	public FilterRegistrationBean registerCorsFiterBean(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CorsFiter domainFilter = new CorsFiter();
        registrationBean.setFilter(domainFilter);
        List<String> urlPatterns = new ArrayList<String>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
	}
	
}
