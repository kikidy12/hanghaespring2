package com.example.hanghaespring2.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {

        // SecuritySecheme명
        String jwtSchemeName = "Authorization";
        // API 요청헤더에 인증정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        Info info = new Info()
                .title("Post API 문서") // 타이틀
                .version("1.0") // 문서 버전
                .description("post api docs") // 문서 설명
                .contact(new Contact()
                        .name("권성민"));

//        return new OpenAPI().info(info).addSecurityItem(securityRequirement)
//                .components(components);
        return new OpenAPI().info(info);
    }
}