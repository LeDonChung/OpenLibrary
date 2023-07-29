package com.open.library.utils.response;

import lombok.Data;

@Data
public class AuthorResponseDTO {
    private Long id;

    private String fullName;

    private String image;

    private String story;

    private boolean is_activated;

    private boolean is_deleted;
}
