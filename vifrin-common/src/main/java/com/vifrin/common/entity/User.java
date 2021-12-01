package com.vifrin.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "last_login")
    private Instant lastLogin;

    @Column(name = "last_ip")
    private String lastIP;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Profile profile;

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.role = "USER";
    }
}