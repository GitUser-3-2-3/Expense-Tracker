package com.sc.userservice.services;

import com.sc.userservice.entities.UserInfo;
import com.sc.userservice.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    /*
    Complicated because I wanted to use FunctionalI, and Supplier.
     */
    @Transactional
    public UserInfo createOrUpdateUser(UserInfo userInfo) {
        UnaryOperator<UserInfo> updateUser = request -> {
            setUserData(userInfo, request);
            return userInfoRepository.save(request);
        };
        Supplier<UserInfo> createUser = () -> userInfoRepository.save(userInfo);

        return userInfoRepository.findByUserId(userInfo.getUserId())
            .map(updateUser).orElseGet(createUser);
    }

    private void setUserData(UserInfo userInfo, UserInfo request) {
        request.setUsername(userInfo.getUsername());
        request.setUserId(userInfo.getUserId());
        request.setUserEmail(userInfo.getUserEmail());
        request.setPassword(userInfo.getPassword());
        request.setPhoneNumber(userInfo.getPhoneNumber());
    }

    public UserInfo getUserById(String userId) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findByUserId(userId);
        if (userInfoOptional.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User does not exists with id::" + userId);
        }
        return userInfoOptional.get();
    }
}