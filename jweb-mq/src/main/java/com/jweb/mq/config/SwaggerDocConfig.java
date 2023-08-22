package com.jweb.mq.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
@Configuration  
@EnableSwagger2
public class SwaggerDocConfig extends WebMvcConfigurationSupport{
	public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.jweb.mq.controller";
    public static final String VERSION = "1.0.0";

    @Bean
    public Docket createRestApi() {
    	
    	ParameterBuilder tokenPar = new ParameterBuilder();
    	tokenPar.name("XA-AUTH").description("TOKEN").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    	List<Parameter> pars = new ArrayList<Parameter>();
    	pars.add(tokenPar.build());
    	
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))//api接口包扫描路径
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build().globalOperationParameters(pars);
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("JWEB MQ	接口文档")//设置文档的标题
            .description("JWEB MQ 接口文档")//设置文档的描述->1.Overview
            .version(VERSION)//设置文档的版本信息-> 1.1 Version information
            .contact(new Contact("邓超", "", "844436845@qq.com"))//设置文档的联系方式->1.2 Contact information
            .termsOfServiceUrl("www.abc.com")//设置文档的License信息->1.3 License information
            .build();
    }
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    	/**
         * SpringBoot自动配置本身并不会把/swagger-ui.html
         * 这个路径映射到对应的目录META-INF/resources/下面
         * 采用WebMvcConfigurerAdapter将swagger的静态文件进行发布;
         */
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
       
    }
}
