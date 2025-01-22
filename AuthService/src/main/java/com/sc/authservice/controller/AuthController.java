package com.sc.authservice.controller;

import com.sc.authservice.entities.UserInfo;
import com.sc.authservice.services.AuthenticationService;
import com.sc.authservice.services.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public AuthController(
        AuthenticationService authenticationService, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationService = authenticationService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
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

    @GetMapping("/ping")
    public ResponseEntity<String> pingAuthorizedEndpoint() {
        Authentication connectedUser = SecurityContextHolder.getContext().getAuthentication();

        if (connectedUser != null && connectedUser.isAuthenticated()) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(connectedUser.getName());
            String username = userDetails.getUsername();
            if (Objects.nonNull(username)) return ResponseEntity.ok(username);
        }
        return ResponseEntity.status(UNAUTHORIZED).body("User unauthorized");
    }
}