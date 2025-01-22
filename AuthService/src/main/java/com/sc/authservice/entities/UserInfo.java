package com.sc.authservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;

    private String username;
    private String password;

    private String userEmail;
    private Long phoneNumber;
}