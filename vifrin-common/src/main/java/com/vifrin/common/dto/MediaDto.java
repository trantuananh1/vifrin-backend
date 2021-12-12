package com.vifrin.common.dto;

import com.vifrin.common.entity.Post;
import com.vifrin.common.entity.User;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Sun, 12/12/2021
 **/

@Data
public class MediaDto {
    private Long id;
    private String url;
    private String mime;
    private String name;
    private float width;
    private float height;
    private float size;
    private Instant createdAt;
    private Instant updatedAt;
    private Long postId;
    private Long userId;
}
