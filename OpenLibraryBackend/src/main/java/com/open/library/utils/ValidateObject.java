package com.open.library.utils;

import com.open.library.utils.request.UserDTO;

import java.util.HashMap;
import java.util.Map;

public class ValidateObject {
    public static Map<String, String> validateUserDTO(UserDTO dto) {
        Map<String, String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("fullName")
                .value(dto.getFullName())
                .required(true)
                .minLength(5)
                .build().validate()
        );

        errors.putAll(ValidateUtils.builder()
                .fieldName("password")
                .value(dto.getPassword())
                .required(true)
                .minLength(6)
                .build().validate()
        );

        errors.putAll(ValidateUtils.builder()
                .fieldName("username")
                .value(dto.getUsername())
                .required(true)
                .build().validate()
        );

        String username = dto.getUsername();
        if(username.contains("@")) {
            errors.putAll(ValidateUtils.builder()
                    .fieldName("username")
                    .value(username)
                    .isEmail(true)
                    .build().validate());
        } else {
            errors.putAll(ValidateUtils.builder()
                    .fieldName("username")
                    .value(username)
                    .isPhone(true)
                    .build().validate());
        }
        return errors;

    }
}
