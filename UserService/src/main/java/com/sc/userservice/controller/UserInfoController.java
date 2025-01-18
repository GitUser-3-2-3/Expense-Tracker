package com.sc.userservice.controller;

import com.sc.userservice.entities.UserInfo;
import com.sc.userservice.services.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<UserInfo> createUpdateUser(@RequestBody UserInfo userInfo) {
        try {
            UserInfo userResponse = userInfoService.createOrUpdateUser(userInfo);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception rtE) {
            log.error("EXCEPTION IN createUpdateUser:: {}", rtE.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserInfo> getUserById(@PathVariable String userId) {
        try {
            UserInfo userResponse = userInfoService.getUserById(userId);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception rtE) {
            log.error("EXCEPTION IN getUserById:: {}", rtE.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}