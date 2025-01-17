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
        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsBytes(userInfoDTO);
            log.info("userInfoDTO converted to bytes:: {}", retVal);
        }
        catch (JsonProcessingException rtExp) {
            rtExp.printStackTrace();
        }
        return retVal;
    }
}