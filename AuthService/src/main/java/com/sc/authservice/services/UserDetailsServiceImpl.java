package com.sc.authservice.services;

import com.sc.authservice.entities.UserInfo;
import com.sc.authservice.model.UserInfoDTO;
import com.sc.authservice.repositories.UserInfoRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

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
            return false;
        }
        String userId = UUID.randomUUID().toString();

        UserInfo userInfo = UserInfo.builder().userId(userId).username(userInfoDTO.getUsername())
            .password(userInfoDTO.getPassword()).roles(new HashSet<>()).build();
        userInfoRepository.save(userInfo);
        return true;
    }
}