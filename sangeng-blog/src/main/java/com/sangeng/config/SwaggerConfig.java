package com.sangeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sangeng.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("stardust", "http://www.baidu.com", "876700669@qq.com");
        return new ApiInfoBuilder()
                .title("JBlog")
                .description("blog后端接口文档")
                .contact(contact)
                .version("1.1.0")
                .build();
    }
}
