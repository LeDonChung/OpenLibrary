package com.open.library.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteResponseDTO {
    private Long id;

    private String content;

    private int likes;

    private boolean is_deleted;

    private boolean is_activated;
}
