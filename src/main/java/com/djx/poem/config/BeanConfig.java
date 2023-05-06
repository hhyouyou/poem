package com.djx.poem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author dongjingxi
 * @date 2023/5/6
 */
@Configuration
public class BeanConfig {


    /**
     * 注入RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
