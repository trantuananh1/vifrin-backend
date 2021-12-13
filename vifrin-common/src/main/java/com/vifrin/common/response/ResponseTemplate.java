package com.vifrin.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 3:20 PM
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTemplate<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ResponseTemplate(ResponseType responseType, T data) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public ResponseTemplate(ResponseType responseType) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = null;
    }
}
