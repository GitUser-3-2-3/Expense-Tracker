package com.sc.authservice.controller;

import com.sc.authservice.model.UserInfoDTO;
import com.sc.authservice.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("auth/v1/")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody UserInfoDTO userInfoDTO) {
        try {
            Object user = authenticationService.signup(userInfoDTO);
            return new ResponseEntity<>(user, CREATED);
        }
        catch (Exception rtExp) {
            return new ResponseEntity<>("Exception in user service", INTERNAL_SERVER_ERROR);
        }
    }
}