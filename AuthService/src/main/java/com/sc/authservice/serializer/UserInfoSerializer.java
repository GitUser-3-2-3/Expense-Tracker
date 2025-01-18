package com.sc.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.authservice.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

@Slf4j
@SuppressWarnings("unused")
public class UserInfoSerializer implements Serializer<UserInfo> {

    @Override
    public byte[] serialize(String s, UserInfo userInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("USERINFO OBJECT:: {}", userInfo);
            byte[] retVal = objectMapper.writeValueAsBytes(userInfo);
            log.info("userInfoDTO converted to bytes:: {}", retVal);
            log.info("CONVERTING TO USERINFO:: {}", objectMapper.readValue(retVal, UserInfo.class));
            return retVal;
        }
        catch (IOException rtExp) {
            log.error("ERROR in serializing UserInfoDTO:: {}", rtExp.getMessage());
            return new byte[0];
        }
    }
}