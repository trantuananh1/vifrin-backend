package com.vifrin.common.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SecondaryTable(name = "activities", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@Table(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "average_score")
    private float averageScore;

    @Column(name = "longitude")
    private float longitude;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;

    @Column(name = "check_in_count", columnDefinition = "int default 0")
    private int checkInsCount;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Post> posts;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Media> medias;

    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Hotel> hotels;


    @Embedded
    private Activity activity;

    @Override
    public String toString() {
        return "Destination{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", checkInsCount=" + checkInsCount +
                ", posts=" + posts +
                ", comments=" + comments +
                ", medias=" + medias +
//                ", hotels=" + hotels +
                ", activity=" + activity +
                '}';
    }
}
