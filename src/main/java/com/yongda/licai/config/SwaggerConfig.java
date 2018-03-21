package com.yongda.licai.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Swagger接口文档配置
 * 作者：徐承恩
 * 邮箱：771247770@qq.com
 * 日期：2017/12/11-下午1:59
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Resource(name = "environment")
    private Environment environment;

    @Bean
    public Docket HomeApi() {
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("x-token") //参数名
                .defaultValue("") //默认值
                .description("登录令牌")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameterBuilder.build());


        //获取当前环境
        String profile = environment.getProperty("profile");
        if (profile.equals("prod")) {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(homeApiInfo())
                    .select()
                    .paths(PathSelectors.none())
                    .build();
        }

        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(parameters)
                .apiInfo(homeApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yongda.licai.system.web.controller.home"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .build();
    }

    private ApiInfo homeApiInfo() {
        return new ApiInfoBuilder()
                .title("永达理财PC端接口文档")
                .version("1.0")
                .build();
    }

}
