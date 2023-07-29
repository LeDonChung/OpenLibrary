package com.open.library.utils.request;

import lombok.Data;


@Data
public class PublisherDTO {
    private Long id;

    private String name;

    private String address;

    private String story;

    private boolean is_activated;

    private boolean is_deleted;
}
