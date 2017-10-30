package com.dsdl.eidea.api.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/9/9 17:48.
 */
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        Parameter parameter=new ParameterBuilder()
                .name("token")
                .description("token认证")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();

        List<Parameter> parameterList=new ArrayList<>();
        List<ResponseMessage> responseMessageList=new ArrayList<>();
        responseMessageList.add(new ResponseMessageBuilder()
                .code(500)
                .message("500 服务器内部错误")
                .responseModel(new ModelRef("String"))
                .build());
        responseMessageList.add( new ResponseMessageBuilder()
                .code(403)
                .message("禁止访问!")
                .responseModel(new ModelRef("com.dsdl.eidea.api.model.ErrorResponse"))
                .build());
        responseMessageList.add( new ResponseMessageBuilder()
                .code(401)
                .responseModel(new ModelRef("com.dsdl.eidea.api.model.ErrorResponse"))
                .message("无权限访问!")
                .build());

        parameterList.add(parameter);
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameterList)
                .globalResponseMessage(RequestMethod.GET,responseMessageList)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dsdl.eidea.base.rest"))
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
                .description(" 401 没有权限<br> 500 服务错误 <br> 404 访问的资源部存在 <br> 200 正常返回")
                .build();
    }
}