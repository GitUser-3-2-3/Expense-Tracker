package com.sc.userservice.consumer;

import com.sc.userservice.entities.UserInfo;
import com.sc.userservice.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceConsumer {

    private final UserInfoService userInfoService;

    @Autowired
    public AuthServiceConsumer(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @KafkaListener(
        topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenEventFromKafka(UserInfo userInfo) {
        log.info("RECEIVED user:: {}", userInfo);
        userInfoService.createOrUpdateUser(userInfo);
    }
}