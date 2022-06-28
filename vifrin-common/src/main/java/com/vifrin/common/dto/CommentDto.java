package com.vifrin.common.dto;

import com.vifrin.common.entity.Media;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Sun, 12/12/2021
 **/

@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private Long destinationId;
    private Long hotelId;
    private String content;
    private Instant createdAt;
    private Instant updatedAt;
    private int likesCount;
    private int star;
    List<Long> mediaIds;
    List<MediaDto> medias;
    private UserSummary user;
}
