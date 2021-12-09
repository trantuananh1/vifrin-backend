package com.vifrin.common.payload;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlicedResult<T> {

    private String pagingState;
    private boolean isLast;
    private List<T> content;
}
