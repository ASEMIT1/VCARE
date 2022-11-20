package com.vcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.vcare.utils.AuthFilter;

@SpringBootApplication 
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
//    @Bean
//    public FilterRegistrationBean<AuthFilter> loggingFilter(){
//        FilterRegistrationBean<AuthFilter> registrationBean 
//          = new FilterRegistrationBean<>();
//            
//        registrationBean.setFilter(new AuthFilter());
//        registrationBean.addUrlPatterns("/api/*");
//            
//        return registrationBean;    
//    }
    
}
