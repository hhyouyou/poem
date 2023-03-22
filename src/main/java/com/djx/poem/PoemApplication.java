package com.djx.poem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author dongjingxi
 * @date 2023/3/21
 */
@EnableAsync
@EnableJpaAuditing
@SpringBootApplication
public class PoemApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PoemApplication.class);

    }





}
