package com.vifrin.common.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private Long destinationId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private int likesCount;
    private int score;
    private UserSummary user;
}
