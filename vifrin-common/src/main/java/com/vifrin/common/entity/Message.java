package com.vifrin.common.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String content;

    @OneToOne
    @JoinColumn(name = "authorId")
    private User author;

    @OneToOne
    @JoinColumn(name = "threadId")
    private Thread thread;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedBy
    private Instant updatedAt;
}
