package com.open.library.utils.response;

import lombok.Data;

import java.util.Collection;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private String image;
    private Integer status;
    private Collection<String> roles;
}
