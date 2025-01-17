package com.sc.userservice.consumer;

import com.sc.userservice.models.UserInfoDTO;
import com.sc.userservice.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceConsumer {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public AuthServiceConsumer(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @KafkaListener(
        topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenEventFromKafka(UserInfoDTO userInfoDTO) {
        log.info("RECEIVED user:: {}", userInfoDTO);
    }
}