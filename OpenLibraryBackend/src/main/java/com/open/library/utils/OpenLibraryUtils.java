package com.open.library.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.library.utils.request.BookDTO;
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

    public static Object getRequest(String dataJson, Class clazz) {
        ObjectMapper mapper = new ObjectMapper();
        Object object = null;
        try {
            object = mapper.readValue(dataJson, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return object;

    }
}
