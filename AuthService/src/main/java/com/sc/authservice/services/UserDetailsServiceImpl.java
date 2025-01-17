package com.sc.authservice.services;

import com.sc.authservice.entities.UserInfo;
import com.sc.authservice.model.UserInfoDTO;
import com.sc.authservice.producer.UserInfoProducer;
import com.sc.authservice.repositories.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoProducer userInfoProducer;

    @Autowired
    public UserDetailsServiceImpl(
        UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder, UserInfoProducer userInfoProducer) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoProducer = userInfoProducer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("Could not find User!!!");
        }
        return new CustomUserDetails(userInfo);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDTO userInfoDTO) {
        return userInfoRepository.findByUsername(userInfoDTO.getUsername());
    }

    public Boolean signupUser(UserInfoDTO userInfoDTO) {
        userInfoDTO.setPassword(passwordEncoder.encode(userInfoDTO.getPassword()));
        if (Objects.nonNull(checkIfUserAlreadyExists(userInfoDTO))) {
            return Boolean.FALSE;
        }
        String userId = UUID.randomUUID().toString();

        UserInfo userInfo = UserInfo.builder().userId(userId).username(userInfoDTO.getUsername())
            .password(userInfoDTO.getPassword()).roles(new HashSet<>()).build();
        userInfoRepository.save(userInfo);

        // Push event to queue
        userInfoProducer.sendEventToKafka(userInfoDTO);
        return Boolean.TRUE;
    }
}