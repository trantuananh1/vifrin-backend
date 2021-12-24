package com.vifrin.common.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author: trantuananh1
 * @since: Tue, 21/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_read", columnDefinition = "boolean default false")
    private boolean isRead;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @CreatedDate
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_object_id", nullable = false)
    private NotificationObject notificationObject;

    public Notification(NotificationObject notificationObject, User user){
        this.notificationObject = notificationObject;
        this.user = user;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
