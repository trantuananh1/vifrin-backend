package com.vifrin.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author: trantuananh1
 * @since: Fri, 10/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "feeds")
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    @NonNull
    private Long userId;

    @Column(name = "username")
    @NonNull
    private String username;

    @Column(name = "post_id")
    @NonNull
    private Long postId;

    @Column(name = "score")
    @NonNull
    private Instant score;

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", userId=" + userId +
                ", username=" + username +
                ", postId=" + postId +
                '}';
    }
}
