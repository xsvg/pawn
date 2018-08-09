package cn.cnplay.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.cnplay.demo.web.interceptor.UserHandleLogInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new LoginCheckInterceptor()).addPathPatterns("/**");
		//registry.addInterceptor(new UserRoleCheckInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(new UserHandleLogInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

}
