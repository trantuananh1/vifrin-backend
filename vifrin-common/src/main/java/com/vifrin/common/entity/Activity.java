package com.vifrin.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author: tranmanhhung
 * @since: Sun, 05/12/2021
 **/

@Embeddable
@Data
@Table(name = "activities")
public class Activity {
    @Column(name = "likes_count")
    private int likesCount;

    @Column(name = "comments_count")
    private int commentsCount;

    @Column(name = "posts_count")
    private int postsCount;

    @Column(name = "followers_count")
    private int followersCount;

    @Column(name = "followings_count")
    private int followingsCount;

    public Activity() {
        likesCount = 0;
        commentsCount = 0;
        postsCount = 0;
        followersCount = 0;
        followingsCount = 0;
    }
}
