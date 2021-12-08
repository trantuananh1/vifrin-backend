package com.vifrin.post;

import com.vifrin.post.messaging.PostEventStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@EnableFeignClients(basePackages = {"com.vifrin.feign"})
@EnableJpaRepositories(basePackages = {"com.vifrin.common"})
@EntityScan(basePackages = {"com.vifrin.common"})
@EnableBinding(PostEventStream.class)
public class PostServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }
}