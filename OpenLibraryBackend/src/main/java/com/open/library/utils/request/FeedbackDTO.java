package com.open.library.utils.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackDTO {
    private Long id;

    private String name;

    private String email;

    private String message;

}
