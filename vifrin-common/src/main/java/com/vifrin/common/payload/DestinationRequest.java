package com.vifrin.common.payload;

import lombok.Data;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Mon, 20/12/2021
 **/

@Data
public class DestinationRequest {
    private String name;
    private String description;
    private float longitude;
    private float latitude;
    private List<Long> mediaIds;
}
