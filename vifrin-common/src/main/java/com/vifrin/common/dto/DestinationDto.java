package com.vifrin.common.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 16/12/2021
 **/

@Data
public class DestinationDto {
    private Long id;
    private String name;
    private String description;
    private float averageScore;
    private float longitude;
    private float latitude;
    private Instant createdAt;
    private Instant updatedAt;
    private int checkInsCount;
    private List<MediaDto> medias;
}
