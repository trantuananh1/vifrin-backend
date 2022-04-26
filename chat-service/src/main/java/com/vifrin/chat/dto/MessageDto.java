package com.vifrin.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Tue, 26/04/2022
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private Long id;

    private String content;

    private String username;

    private Instant createdAt;

    private Instant updatedAt;
}
