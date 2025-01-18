package com.sc.authservice.controller;

import com.sc.authservice.entities.UserInfo;
import com.sc.authservice.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserInfo userInfo) {
        log.info("USERINFO API:: {}", userInfo);
        try {
            Object user = authenticationService.signup(userInfo);
            return new ResponseEntity<>(user, OK);
        }
        catch (Exception rtExp) {
            return new ResponseEntity<>(
                "Exception in user service " + rtExp.getMessage(), INTERNAL_SERVER_ERROR);
        }
    }
}