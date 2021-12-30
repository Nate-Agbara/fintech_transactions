package com.fintech.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(appinfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fintech.transaction.controller"))
                //Predicate.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo appinfo(){
        return new ApiInfoBuilder().title("Simple Account Transaction API")
                .description("Basic transaction implementation for an account. Deposit and getStatus")
                .version("V1.0").build();
    }

}
