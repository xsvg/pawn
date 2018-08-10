package cn.cnplay.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cn.cnplay.demo.web.interceptor.LoginCheckInterceptor;
import cn.cnplay.demo.web.interceptor.UserHandleLogInterceptor;
import cn.cnplay.demo.web.interceptor.UserRoleCheckInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	LoginCheckInterceptor getLoginCheckInterceptor(){
		return new LoginCheckInterceptor();
	}
	@Bean
	UserRoleCheckInterceptor getUserRoleCheckInterceptor(){
		return new UserRoleCheckInterceptor();
	}
	@Bean
	UserHandleLogInterceptor getUserHandleLogInterceptor(){
		return new UserHandleLogInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getLoginCheckInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(getUserRoleCheckInterceptor()).addPathPatterns("/**");
		registry.addInterceptor(getUserHandleLogInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

}
