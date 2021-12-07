package com.vifrin.common.entity;

import lombok.*;
import org.json.JSONObject;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Sun, 05/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
@SecondaryTable(name = "activities", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name= "image_url")
    private String imageUrl;

    @Column(name = "has_detail")
    private boolean hasDetail;

    @Column(name = "detail")
    private String detail;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;

    @Column(name = "config")
    private String config;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private Activity activity;

    public Post(String content, String imageUrl, boolean hasDetail, String detail, String config, User user){
        this.content = content;
        this.imageUrl = imageUrl;
        this.hasDetail = hasDetail;
        this.detail = detail;
        this.config = config;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", hasDetail=" + hasDetail +
                ", detail='" + detail + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", config='" + config + '\'' +
                ", user=" + user +
                ", activity=" + activity +
                '}';
    }
}
