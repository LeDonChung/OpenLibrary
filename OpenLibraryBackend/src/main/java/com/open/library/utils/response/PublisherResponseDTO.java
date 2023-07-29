package com.open.library.utils.response;

import lombok.Data;

@Data
public class PublisherResponseDTO {
    private Long id;

    private String name;

    private String address;

    private String story;

    private boolean is_activated;

    private boolean is_deleted;
}
