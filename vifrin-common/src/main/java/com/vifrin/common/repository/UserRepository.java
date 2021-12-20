package com.vifrin.common.repository;

import com.vifrin.common.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail (String email);

    @Query(value = "SELECT followers_id FROM users_followers WHERE followings_id=?1",
            countQuery = "SELECT count(*) FROM users_followers WHERE followings_id=?1",
            nativeQuery = true)
    List<Long> getFollowersByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT followings_id FROM users_followers WHERE followers_id=?1",
        countQuery = "SELECT count(*) FROM users_followers WHERE followers_id=?1",
        nativeQuery = true)
    List<Long> getFollowingsByUserId(Long userId, Pageable pageable);

    List<User> findByUsernameContainingIgnoreCase(String key);

    List<User> findByFullNameContainingIgnoreCase(String key);
}
