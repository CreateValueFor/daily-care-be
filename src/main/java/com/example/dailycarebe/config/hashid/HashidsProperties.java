package com.example.dailycarebe.config.hashid;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "hashids")
public class HashidsProperties {
    private String salt;
    private String seps;
    private int minLength;
    private String alphabet;
}
