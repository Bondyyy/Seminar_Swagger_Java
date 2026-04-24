package com.nhieuhanh.__demo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hệ thống Quản lý Sinh viên API")
                        .version("1.0.0")
                        .description("Đây là tài liệu API mô phỏng các thao tác CRUD cơ bản cho thực thể Student. Tài liệu này giúp front-end dễ dàng tích hợp và test dữ liệu.")
                        .contact(new Contact()
                                .name("Huỳnh Đức Dũng")
                                .email("bondy@example.com")
                                .url("https://github.com/bondy"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Môi trường Local (Dev)"),
                        new Server().url("https://api.nhieuhanh.com").description("Môi trường Production")
                ));
    }
}