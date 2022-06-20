package com.vifrin.common.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author: tranmanhhung
 * @since: Thu, 16/12/2021
 **/

@Data
public class StatisticRatingDto {
    private int oneStar;
    private int twoStar;
    private int threeStar;
    private int fourStar;
    private int fiveStar;
}
