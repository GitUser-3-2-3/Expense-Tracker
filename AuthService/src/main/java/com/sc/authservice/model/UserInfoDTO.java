package com.sc.authservice.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sc.authservice.entities.UserInfo;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserInfoDTO extends UserInfo {

    private String username;
    private String password;
    private String userEmail;

    private Long userPhoneNumber;
}