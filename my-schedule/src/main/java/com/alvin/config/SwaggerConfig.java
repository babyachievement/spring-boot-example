package com.alvin.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by Administrator on 2017/4/20.
 */
@Configuration
@EnableSwagger2 // @EnableSwagger2 annotation enables Swagger support in the class
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        // apis() and paths() methods to filter the controllers and methods being documented using String predicates
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alvin"))
                .paths(paths())
                .build().apiInfo(metaData());

    }


    /**
     * 可以在or中继续添加
     * @return
     */
    private Predicate<String> paths() {
        return or(
                regex("/books.*"));
    }


    // customizing Swagger by providing information about our API
    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Personal Schedule",
                "1.0",
                "Terms of service",
                new Contact("Alvin Nathan", "https://github.com/babyachievement", "babyachievement@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }
}