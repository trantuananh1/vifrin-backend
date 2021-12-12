package com.vifrin.common.payload;

import lombok.Data;
import org.json.JSONObject;

import javax.persistence.Column;
import java.time.Instant;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Mon, 06/12/2021
 **/

@Data
public class PostRequest {
    private String content;
    private List<String> imageUrls;
    private String config;
}
