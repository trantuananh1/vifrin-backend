package com.vifrin.common.repository;

import com.vifrin.common.entity.Feed;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Sat, 11/12/2021
 **/

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    @Query(value = "SELECT post_id FROM feeds WHERE username=?1 ORDER BY score DESC", nativeQuery = true)
    List<Long> getFeedByUsername(String username, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM feeds WHERE username=?1 AND post_id=?2", nativeQuery = true)
    Long getFeedCountByUsernameAndPostId(String username, Long postId);

    List<Feed> findByPostId(Long postId);
}
