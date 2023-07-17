package com.example.dailycarebe.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomAuthToken implements Serializable {
    @JsonProperty("client_id")
    private String clientId;

    private String jti;

    @JsonProperty("user_name")
    @Setter
    private String loginId;

    @Setter
    private String email;

    private String userUuid;

    private String customerUuid;

    private long userId;

    private long customerId;

    private long exp;
}
