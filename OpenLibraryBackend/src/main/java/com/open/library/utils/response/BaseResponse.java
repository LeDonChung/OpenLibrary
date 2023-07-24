package com.open.library.utils.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BaseResponse<T> {
    private T data;
    private boolean success;
    private String code;
}
