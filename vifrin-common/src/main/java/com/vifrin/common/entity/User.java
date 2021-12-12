package com.vifrin.common.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SecondaryTable(name = "activities", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

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

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Profile profile;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Media> medias;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @ManyToMany
    private Set<User> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<User> followings;

    @Embedded
    private Activity activity;

    public User(String username, String password, String email) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.role = "USER";
        this.activity = new Activity();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", lastLogin=" + lastLogin +
                ", lastIP='" + lastIP + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", role=" + role +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}