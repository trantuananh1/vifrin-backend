package com.vifrin.common.payload;

import lombok.Data;

import java.util.List;

/**
 * @author: trantuananh1
 * @since: Mon, 20/12/2021
 **/

@Data
public class HotelRequest {
    private String name;
    private String description;
    private float longitude;
    private float latitude;
    private float price;
    private String phone;
    private long destinationId;
    private List<Long> mediaIds;
}
