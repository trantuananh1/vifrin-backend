package com.vifrin.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long authorId;
    private long threadId;
    private Instant createdAt;
    private Instant updatedAt;
}
