package com.vifrin.common.response;

import com.vifrin.common.payload.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @Author: trantuananh1
 * @Created: Thu, 02/09/2021 3:20 PM
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResponseTemplate implements Serializable {
    private int code;
    private String message;
    private Object data;
    private UserDto userDto;

    public ResponseTemplate(ResponseType responseType, Object data) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.data = data;
    }

    public ResponseTemplate(ResponseType responseType, UserDto userDto) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
        this.userDto = userDto;
        this.data = userDto;
    }
}
