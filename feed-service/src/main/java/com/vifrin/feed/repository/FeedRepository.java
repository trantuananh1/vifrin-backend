package com.vifrin.feed.repository;

import com.vifrin.feed.entity.UserFeed;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * @author: trantuananh1
 * @since: Thu, 09/12/2021
 **/

public interface FeedRepository extends CassandraRepository<UserFeed, String> {
    Slice<UserFeed> findByUsername(String username, Pageable pageable);
}