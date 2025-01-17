package com.sc.userservice.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.userservice.models.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

@Slf4j
public class UserInfoDeserializer implements Deserializer<UserInfoDTO> {

    @Override
    public UserInfoDTO deserialize(String key, byte[] value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(value, UserInfoDTO.class);
        }
        catch (IOException ioExp) {
            log.error("ERROR in deserializing UserInfoDTO:: {}", ioExp.getMessage());
            return new UserInfoDTO();
        }
    }
}