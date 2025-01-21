package com.sc.expenseservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sc.expenseservice.dto.ExpenseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ExpenseDeserializer implements Deserializer<ExpenseDTO> {

    @Override
    public ExpenseDTO deserialize(String key, byte[] value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ExpenseDTO expenseDTO = objectMapper.readValue(value, ExpenseDTO.class);
            log.info("EVENT_DATA:: {}", expenseDTO);
            return expenseDTO;
        }
        catch (IOException ioExp) {
            log.error("ERROR in deserializing ExpenseDTO):: {}", ioExp.getMessage());
            return new ExpenseDTO();
        }
    }
}