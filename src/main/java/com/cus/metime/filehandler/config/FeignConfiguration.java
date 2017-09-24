package com.cus.metime.filehandler.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.cus.metime.filehandler")
public class FeignConfiguration {

}
