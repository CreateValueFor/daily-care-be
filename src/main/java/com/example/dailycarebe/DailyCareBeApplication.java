package com.example.dailycarebe;

import com.example.dailycarebe.config.SkipFeignClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ServletComponentScan
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = SkipFeignClientConfig.class))
@EnableFeignClients
@SpringBootApplication
public class DailyCareBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyCareBeApplication.class, args);
    }

}
