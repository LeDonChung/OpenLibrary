package com.open.library.utils.request;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String email;
    private String image;
}
