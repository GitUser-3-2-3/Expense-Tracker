package com.sc.authservice.models;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "roles")
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;

    private String roleName;
}