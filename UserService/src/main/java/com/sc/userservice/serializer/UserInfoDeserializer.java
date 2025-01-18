package com.sc.userservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.userservice.entities.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class UserInfoDeserializer implements Deserializer<UserInfo> {

    @Override
    public UserInfo deserialize(String key, byte[] value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            UserInfo userInfo = objectMapper.readValue(value, UserInfo.class);
            log.info("EVENT_DATA:: {}", userInfo);
            return userInfo;
        }
        catch (IOException ioExp) {
            log.error("ERROR in deserializing UserInfo:: {}", ioExp.getMessage());
            return new UserInfo();
        }
    }
}