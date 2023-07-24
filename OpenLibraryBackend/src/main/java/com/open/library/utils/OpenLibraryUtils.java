package com.open.library.utils;

import com.open.library.utils.response.BaseResponse;
import org.springframework.stereotype.Component;

public class OpenLibraryUtils {
    public static BaseResponse getResponse(Object data, Boolean success, String code) {
        return BaseResponse.builder()
                .data(data)
                .success(success)
                .code(code)
                .build();
    }
}
