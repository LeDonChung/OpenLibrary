package com.open.library.mapper;

import com.open.library.POJO.Role;
import com.open.library.POJO.User;
import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponseDTO toResponseDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setImage(user.getImage());
        dto.setStatus(user.getStatus());

        if(user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream().map((role) -> role.getName()).collect(Collectors.toList()));
        }

        return dto;
    }
    public User toEntity(UserDTO dto) {
        User entity = new User();
        entity.setFullName(dto.getFullName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    public User toEntity(User userOld, UserDTO userNew) {
        userOld.setFullName(userNew.getFullName());
        userOld.setPhoneNumber((userNew.getPhoneNumber()));
        userOld.setEmail(userNew.getEmail());
        return userOld;
    }
}
