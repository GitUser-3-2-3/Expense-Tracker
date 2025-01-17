package com.sc.authservice.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.authservice.model.UserInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;

@Slf4j
public class UserInfoSerializer implements Serializer<UserInfoDTO> {

    @Override
    public byte[] serialize(String s, UserInfoDTO userInfoDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            byte[] retVal = objectMapper.writeValueAsBytes(userInfoDTO);
            log.info("userInfoDTO converted to bytes:: {}", retVal);
            return retVal;
        }
        catch (JsonProcessingException rtExp) {
            log.error("ERROR in serializing UserInfoDTO:: {}", rtExp.getMessage());
            return new byte[0];
        }
    }
}