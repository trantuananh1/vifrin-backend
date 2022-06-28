package com.vifrin.common.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author: tranmanhhung
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
    private Instant createdAt;
    private Instant updatedAt;
    private List<MediaDto> medias;
    private long destinationId;
}
