package com.shaoume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@org.springframework.data.web.config.EnableSpringDataWebSupport(pageSerializationMode = org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@org.springframework.data.jpa.repository.config.EnableJpaAuditing
@ComponentScan(basePackages = "com.shaoume")
public class ShaoumeglobalBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShaoumeglobalBusinessApplication.class, args);
    }
}