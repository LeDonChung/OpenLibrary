package com.open.library.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChangePasswordDTO {
    private String username;

    private String passwordOld;

    private String passwordNew;
}
