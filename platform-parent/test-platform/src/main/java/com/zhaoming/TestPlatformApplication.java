package com.zhaoming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 *
 * @author zm
 * @create 2017/12/29 上午10:24
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.zhaoming"})
@ComponentScan(basePackages = {"com.zhaoming"})
public class TestPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestPlatformApplication.class, args);
    }
}
