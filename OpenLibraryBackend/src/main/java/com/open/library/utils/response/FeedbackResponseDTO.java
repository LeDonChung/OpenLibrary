package com.open.library.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackResponseDTO {
    private Long id;

    private String name;

    private String email;

    private String message;

    private String responseMessage;

    private boolean status;

}
