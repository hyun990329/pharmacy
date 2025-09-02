package com.my.phamacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
// 스프링 시작 될 때 @configuration 을 읽어서 Bean 으로 등록해둔다
// 사용자는 생성자 주입 또는 @Autowired 를 통해 사용
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
