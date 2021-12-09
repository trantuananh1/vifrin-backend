package com.vifrin.common.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Thu, 09/12/2021
 **/

@Table("user_feeds")
@Data
@Builder
@ToString
public class UserFeed {
    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Long userId;

    @Indexed
    @PrimaryKeyColumn(name = "username", ordinal = 1 )
    private String username;

    @PrimaryKeyColumn(name = "created_at", ordinal = 2, ordering = Ordering.DESCENDING)
    private Instant createdAt;

    @PrimaryKeyColumn(name = "post_id", ordinal = 3)
    private Long postId;
}
