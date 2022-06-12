package com.vifrin.common.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "mime")
    private String mime;

    @Column(name = "name")
    private String name;

    @Column(name = "width")
    private float width;

    @Column(name = "height")
    private float height;

    @Column(name = "size")
    private float size;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Media(String url, String name, String mime, float width, float height, float size, User user) {
        this.url = url;
        this.mime = mime;
        this.name = name;
        this.width = width;
        this.height = height;
        this.size = size;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.user = user;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", mime='" + mime + '\'' +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", size=" + size +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", post=" + post +
                ", user=" + user +
                ", destination=" + destination +
                ", hotel=" + hotel +
                '}';
    }
}
