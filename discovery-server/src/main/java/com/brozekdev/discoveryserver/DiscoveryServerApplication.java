package com.brozekdev.discoveryserver;

import config.BasicAuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
        FilterRegistrationBean<BasicAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new BasicAuthFilter());
        registrationBean.addUrlPatterns("/eureka/*");
        return registrationBean;
    }

}
