package com.vifrin.common.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;

/**
 * @author: tranmanhhung
 * @since: Tue, 21/12/2021
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification_objects")
public class NotificationObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private int type;

    @Column(name = "entity_id")
    private Long entityId;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;

    public NotificationObject(int type, Long entityId) {
        this.type = type;
        this.entityId = entityId;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
