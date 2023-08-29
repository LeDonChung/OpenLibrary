package com.open.library.utils;

import com.open.library.utils.request.*;

import java.util.HashMap;
import java.util.Map;

public class ValidateObject {
    public static Map<String, String> validateCategoryDTO(CategoryDTO dto) {
        Map<String, String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("name")
                .value(dto.getName())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("code")
                .value(dto.getCode())
                .required(true)
                .build().validate());
        return errors;
    }

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
        if (username.contains("@")) {
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

    public static Map<String, String> validateAuthorDTO(AuthorDTO dto) {
        Map<String, String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("fullName")
                .value(dto.getFullName())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("story")
                .value(dto.getStory())
                .required(true)
                .build().validate());
        return errors;
    }

    public static Map<String, String> validateBookDTO(BookDTO dto) {
        Map<String, String> errors = new HashMap<>();
        errors.putAll((ValidateUtils.builder()
                .fieldName("title")
                .value(dto.getTitle())
                .required(true)
                .build()
                .validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("isbn")
                .value(dto.getIsbn())
                .required(true)
                .isIsbn(true)
                .build()
                .validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("numberOfPages")
                .value(dto.getNumberOfPages())
                .required(true)
                .isInteger(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("description")
                .value(dto.getDescription())
                .required(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("publishDate")
                .value(dto.getPublishDate())
                .required(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("language")
                .value(dto.getLanguage())
                .required(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("categories")
                .value(dto.getCategories())
                .required(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("publisher")
                .value(dto.getPublisher())
                .required(true)
                .build().validate()));

        errors.putAll((ValidateUtils.builder()
                .fieldName("authors")
                .value(dto.getAuthors())
                .required(true)
                .build().validate()));
        return errors;
    }

    public static Map<String, String> validatePublisherDTO(PublisherDTO dto) {
        Map<String, String> errors = new HashMap<>();

        errors.putAll(ValidateUtils.builder()
                .fieldName("name")
                .value(dto.getName())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("address")
                .value(dto.getAddress())
                .required(true)
                .build().validate());

        errors.putAll(ValidateUtils.builder()
                .fieldName("story")
                .value(dto.getStory())
                .required(true)
                .build().validate());
        return errors;
    }

    public static Map<String, String> validateFeedbackDTO(FeedbackDTO dto) {
        Map<String, String> errors = new HashMap<>();
        errors.putAll(
                ValidateUtils.builder()
                        .fieldName("name")
                        .value(dto.getName())
                        .required(true)
                        .build().validate()
        );

        errors.putAll(
                ValidateUtils.builder()
                        .fieldName("email")
                        .value(dto.getEmail())
                        .required(true)
                        .isEmail(true)
                        .build().validate()
        );

        errors.putAll(
                ValidateUtils.builder()
                        .fieldName("message")
                        .value(dto.getMessage())
                        .required(true)
                        .build().validate()
        );
        return errors;
    }

    public static Map<String, String> validateQuoteDTO(QuoteDTO quoteDTO) {
        Map<String, String> errors = new HashMap<>();
        errors.putAll(
                ValidateUtils.builder()
                        .fieldName("content")
                        .value(quoteDTO.getContent())
                        .required(true)
                        .build().validate()
        );
        return errors;
    }
}
