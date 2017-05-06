package com.dsdl.eidea.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by 刘大磊 on 2016/9/9 17:48.
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dsdl.eidea.base.api"))
                .paths(PathSelectors.any())
                .build();
    }
    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("eidea api接口")
                .description("api接口服务器")
                .termsOfServiceUrl("http://www.dsdl.com")
                .version("1.0")
                .build();
    }
}