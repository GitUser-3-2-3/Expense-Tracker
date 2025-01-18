package com.sc.authservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.authservice.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UserInfoSerializer implements Serializer<UserInfo> {

    @Override
    public byte[] serialize(String s, UserInfo userInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] retVal = objectMapper.writeValueAsBytes(userInfo);
            log.info("DESERIALIZING USERINFO:: {}", objectMapper.readValue(retVal, UserInfo.class));
            return retVal;
        }
        catch (IOException rtExp) {
            log.error("ERROR in serializing UserInfoDTO:: {}", rtExp.getMessage());
            return new byte[0];
        }
    }
}