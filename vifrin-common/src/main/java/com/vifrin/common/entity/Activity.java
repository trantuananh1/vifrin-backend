package com.vifrin.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Embeddable
@Data
@Table(name = "activities")
public class Activity {
    @Column(name = "likes_count")
    private Long likesCount = 0L;

    @Column(name = "comments_count")
    private Long commentsCount = 0L;

    @Column(name = "posts_count")
    private Long postsCount = 0L;

    @Column(name = "followers_count")
    private Long followersCount= 0L;

    @Column(name = "followings_count")
    private Long followingsCount = 0L;
}
