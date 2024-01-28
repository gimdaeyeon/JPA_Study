package com.jpa.filter.config;

import com.jpa.filter.filter.FirstFilter;
import com.jpa.filter.filter.SecondFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<FirstFilter> firstFilter(){
        FilterRegistrationBean<FirstFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new FirstFilter());
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(1);
        registrationBean.setName("first");

        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<SecondFilter> secondFilter(){
        FilterRegistrationBean<SecondFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new SecondFilter());
        registrationBean.addUrlPatterns("/user/*");
        registrationBean.setOrder(2);
        registrationBean.setName("second");

        return registrationBean;
    }


}
