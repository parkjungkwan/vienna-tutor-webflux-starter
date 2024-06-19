package com.example.demo.common.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
     @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .consumes(getConsumeContentTypes())
            .produces(getProduceContentTypes())
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
            .paths(PathSelectors.any())
            .build()
            .genericModelSubstitutes(Mono.class, Flux.class);   
            // Response Body는 빈 배열( {} ) 처리가 되지 않는 문제를 해결하기 위해 추가
    }

    private Set<String> getConsumeContentTypes() {
        Set<String> consumeContentTypes = new HashSet<>();

        consumeContentTypes.add("application/json;charset=UTF-8");
        consumeContentTypes.add("application/x-www-form-urlencoded");

        return consumeContentTypes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produceContentTypes = new HashSet<>();

        produceContentTypes.add("application/json;charset=UTF-8");

        return produceContentTypes;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Gommunity API")
            .description("Gommunity API")
            .build();
    }
    
}
