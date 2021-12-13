package com.vifrin.common.repository;

import com.vifrin.common.entity.Like;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author: trantuananh1
 * @since: Mon, 13/12/2021
 **/

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT CASE WHEN EXISTS(SELECT * FROM likes WHERE user_id=?1 AND post_id=?2) THEN 'TRUE' ELSE 'FALSE' END",
            nativeQuery = true)
    Boolean existsByUserIdAndPostId(Long userId, Long postId);

    @Query(value = "SELECT * FROM likes WHERE user_id=?1 AND post_id=?2", nativeQuery = true)
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    @Query(value = "SELECT * FROM likes WHERE post_id=?1 ORDER BY created_at DESC", nativeQuery = true)
    List<Like> getLikesByPostId(Long postId, Pageable pageable);
}
