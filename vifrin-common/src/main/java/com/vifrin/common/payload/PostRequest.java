package com.vifrin.common.payload;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Column;
import java.time.Instant;

/**
 * @author: trantuananh1
 * @since: Mon, 06/12/2021
 **/

@Data
public class PostRequest {
    private String content;
    private String imageUrl;
    private boolean hasDetail;
    private String detail;
    private String config;
}
