package com.open.library.utils.response;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;

    private String name;

    private String code;

    private boolean is_activated;

    private boolean is_deleted;
}
