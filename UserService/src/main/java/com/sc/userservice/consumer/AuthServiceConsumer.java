package com.sc.userservice.consumer;

import com.sc.userservice.entities.UserInfo;
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
    public void listenEventFromKafka(UserInfo userInfo) {
        log.info("RECEIVED user:: {}", userInfo);
        userInfoRepository.save(userInfo);
    }
}