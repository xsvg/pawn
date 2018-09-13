package cn.cnplay.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author by peixere@qq.com on 2018年5月7日 下午4:22:53
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2
{
	@Bean
	public Docket createRestApi()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(Bootstrap.class.getPackage().getName()))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo()
	{
		return new ApiInfoBuilder()
				.title("接口文档")
				.description("desc")
				.termsOfServiceUrl("")
				.contact(new Contact("Pei Shaoguo", "http://www.cnplay.cn/", "peixere@qq.com"))
				.version("1.0")
				.build();
	}
}
