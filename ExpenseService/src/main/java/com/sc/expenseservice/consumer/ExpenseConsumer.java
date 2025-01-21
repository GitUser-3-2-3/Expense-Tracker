package com.sc.expenseservice.consumer;

import com.sc.expenseservice.dto.ExpenseDTO;
import com.sc.expenseservice.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExpenseConsumer {

    private final ExpenseService expenseService;

    public ExpenseConsumer(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @KafkaListener(
        topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenEventFromKafka(ExpenseDTO expenseDTO) {
        log.info("RECEIVED expenseDTO:: {}", expenseDTO);
        expenseService.createOrUpdateExpense(expenseDTO);
    }
}