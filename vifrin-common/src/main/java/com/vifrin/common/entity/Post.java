package com.vifrin.common.entity;

import lombok.*;
import org.json.JSONObject;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Media> medias;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Embedded
    private Activity activity;

    public Post(String content, List<Media> medias, String config, User user){
        this.content = content;
        this.medias = medias;
        this.config = config;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.user = user;
        this.activity = new Activity();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", config='" + config + '\'' +
                '}';
    }
}
