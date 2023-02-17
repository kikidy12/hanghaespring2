package com.example.hanghaespring2.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Sort;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class OpenEntityManagerConfig {
//    @Bean
//    public FilterRegistrationBean<OpenEntityManagerInViewFilter> openEntityManagerInViewFilter() {
//        FilterRegistrationBean<OpenEntityManagerInViewFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new OpenEntityManagerInViewFilter());
//        registrationBean.addUrlPatterns("/api/*"); // Set the URL pattern to /api/**
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 예시를 위해 최우선 순위로 Filter 등록
//        return registrationBean;
//    }
}