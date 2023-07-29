package com.open.library.utils.request;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class AuthorDTO {
    private Long id;

    private String fullName;

    private String image;

    private String story;

    private boolean is_activated;

    private boolean is_deleted;
}
