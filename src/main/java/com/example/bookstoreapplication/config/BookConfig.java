package com.example.bookstoreapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class BookConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("Book Store Project").apiInfo(apiInfo()).select()
                .paths(PathSelectors.any()).build();
    }
//    @Bean
//    public Docket postsApi2() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("Book Store").apiInfo(apiInfo()).select()
//                .paths(regex("/book.*")).build();
//    }
//    @Bean
//    public Docket postsApi3() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("Cart Details").apiInfo(apiInfo()).select()
//                .paths(regex("/cart.*")).build();
//    }
//    @Bean
//    public Docket postsApi4() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("Order Details").apiInfo(apiInfo()).select()
//                .paths(regex("/orders.*")).build();
//    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Book Service")
                .description("Sample Documentation Generated Using SWAGGER2 for our Book Rest API")
                .termsOfServiceUrl("https://www.youtube.com/channel/UCORuRdpN2QTCKnsuEaeK-kQ")
                .license("Book License")
                .licenseUrl("https://www.youtube.com/channel/UCORuRdpN2QTCKnsuEaeK-kQ").version("1.0").build();
    }
    //http://localhost:9090/swagger-ui.html
}
