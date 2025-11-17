package com.cometogo.ctg.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final UserBanReleaseInterceptor userBanReleaseInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userBanReleaseInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/management/**");
    }
}
