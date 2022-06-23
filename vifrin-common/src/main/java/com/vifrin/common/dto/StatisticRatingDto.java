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
    private int oneStar = 0;
    private int twoStar = 0;
    private int threeStar = 0;
    private int fourStar = 0;
    private int fiveStar=0;
}
