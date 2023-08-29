package com.open.library.utils.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDTO {
    private Long id;

    private String content;

    private int likes;

    private boolean is_deleted;

    private boolean is_activated;
}
