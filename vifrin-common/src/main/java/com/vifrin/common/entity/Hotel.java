package com.vifrin.common.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

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
@Table(name = "hotel")
public class Hotel {
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

    @Column(name = "price")
    private float price;

    @Column(name = "sales_price")
    private float salesPrice;

    @Column(name = "phone")
    private String phone;

    @Column(name = "has_swimming_pool")
    private boolean hasSwimmingPool;

    @Column(name = "has_parking")
    private boolean hasParking;

    @Column(name = "has_wifi")
    private boolean hasWifi;

    @Column(name = "has_air_conditioner")
    private boolean hasAirConditioner;

    @Column(name = "has_elevator")
    private boolean hasElevator;

    @Column(name = "has_restaurant")
    private boolean hasRestaurant;

    @Column(name = "has_bathroom")
    private boolean hasBathroom;

    @Column(name = "address")
    private String address;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Media> medias;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @Embedded
    private Activity activity;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", averageScore=" + averageScore +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", price=" + price +
                ", phone='" + phone + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
