package com.sc.userservice.repository;

import com.sc.userservice.models.UserInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoDTO, String> {

    UserInfoDTO findByUserId(String userId);
}