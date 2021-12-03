package com.vifrin.common.repository;

import com.vifrin.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail (String email);
}
