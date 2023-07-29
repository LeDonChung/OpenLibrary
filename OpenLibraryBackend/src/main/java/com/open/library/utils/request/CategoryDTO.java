package com.open.library.utils.request;

import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    private String name;

    private String code;

    private boolean is_activated;

    private boolean is_deleted;
}
