package com.vifrin.common.dto;

import com.vifrin.common.entity.Destination;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 16/12/2021
 **/

@Data
public class HotelDto {
    private Long id;
    private String name;
    private String description;
    private float averageScore;
    private float longitude;
    private float latitude;
    private float price;
    private String phone;
    private Instant createdAt;
    private Instant updatedAt;
    private List<MediaDto> medias;
    private List<CommentDto> comments;
    private Destination destination;
}
