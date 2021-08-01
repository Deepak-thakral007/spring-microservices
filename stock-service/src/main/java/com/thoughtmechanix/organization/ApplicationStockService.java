package com.thoughtmechanix.organization;
//http://localhost:8084/swagger-ui/
//http://localhost:8084/v2/api-docs
import com.thoughtmechanix.organization.utils.UserContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//import org.springframework.cloud.stream.messaging.Source;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import javax.servlet.Filter;


@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
//@EnableBinding(Source.class)
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@EnableResourceServer
@EnableKafka
@EnableSwagger2
@ComponentScan(basePackages = {"com.thoughtmechanix.organization"})
@ComponentScan("com.thoughtmechanix.organization.taskexecutor")
public class ApplicationStockService {
    @Bean
    public Filter userContextFilter() {
        UserContextFilter userContextFilter = new UserContextFilter();
        return userContextFilter;
    }

    @Bean
    public TaskExecutor defineTaskExecutor()
    {
        return new TaskExecutor ();
    }
   /* @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.thoughtmechanix.organization.controllers")).build();
    }*/
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStockService.class, args);
    }
}
