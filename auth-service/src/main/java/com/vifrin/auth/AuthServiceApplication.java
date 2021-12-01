package com.vifrin.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.vifrin.feign"})
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = {"com.vifrin.common"})
@EntityScan(basePackages = {"com.vifrin.common"})
@EnableAsync
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new CustomErrorDecoder();
//    }

}
