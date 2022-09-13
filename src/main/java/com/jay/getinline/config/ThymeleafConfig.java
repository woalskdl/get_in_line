package com.jay.getinline.config;

import org.springframework.context.annotation.Configuration;

/**
 * application.yml 을 통해 properties 설정을 추가하기 위한 configuration
 */
@Configuration
public class ThymeleafConfig {

    /*@Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.isDecoupledLogic());
        return defaultTemplateResolver;
    }

    @Getter
    @RequiredArgsConstructor
    @ConstructorBinding
    @ConfigurationProperties("spring.thymeleaf3")
    public static class Thymeleaf3Properties {

        *//**
     * Thymeleaf 3 Decoupled logic 기능 활성화
     *//*
        private final boolean decoupledLogic;
    }*/
}
