package com.thoughtmechanix.licenses;

import com.thoughtmechanix.licenses.config.ServiceConfig;

import com.thoughtmechanix.licenses.utils.UserContextInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
/*import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;*/

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableBinding(Sink.class)
//@EnableResourceServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Application {

    @Autowired
    private ServiceConfig serviceConfig;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

   /*
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }*/
   @LoadBalanced
   @Bean
   public RestTemplate getRestTemplate(){
       RestTemplate template = new RestTemplate();
       List interceptors = template.getInterceptors();
       if (interceptors==null){
           template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
       }
       else{
           interceptors.add(new UserContextInterceptor());
           template.setInterceptors(interceptors);
       }

       return template;
   }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        jedisConnFactory.setHostName( serviceConfig.getRedisServer());
        jedisConnFactory.setPort( serviceConfig.getRedisPort() );
        return jedisConnFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
