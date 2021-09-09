package com.vifrin.common.response;

import com.vifrin.common.type.ResponseType;
import lombok.Data;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 3:20 PM
 **/

@Data
public class ResponseTemplate {
    private int status;
    private String message;
    private Object data;

    public ResponseTemplate(ResponseType responseType, Object data) {
        this.status = responseType.getStatus();
        this.message = responseType.getMessage();
        this.data = data;
    }
}
