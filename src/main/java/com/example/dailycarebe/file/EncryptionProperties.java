package com.example.dailycarebe.file;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "encryption")
public class EncryptionProperties {
  String key;
}

