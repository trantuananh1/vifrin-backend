package com.vifrin.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: trantuananh1
 * @since: Tue, 03/05/2022
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDto {
    long id;
    String userOneFullName;
    String userTwoFullName;
}
