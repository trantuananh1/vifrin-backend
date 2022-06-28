package com.vifrin.common.payload;

import lombok.Data;

import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Mon, 20/12/2021
 **/

@Data
public class HotelRequest {
    private String name;
    private String description;
    private float longitude;
    private float latitude;
    private float price;
    private float salesPrice;
    private String phone;
    private boolean hasSwimmingPool;
    private boolean hasParking;
    private boolean hasWifi;
    private boolean hasAirConditioner;
    private boolean hasElevator;
    private boolean hasRestaurant;
    private boolean hasBathroom;
    private String address;
    private long destinationId;
    private List<Long> mediaIds;
}
