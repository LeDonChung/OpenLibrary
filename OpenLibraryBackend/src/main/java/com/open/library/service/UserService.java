package com.open.library.service;

import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    ResponseEntity<BaseResponse> register(UserDTO user);

    ResponseEntity<BaseResponse> getAllUser();

    ResponseEntity<BaseResponse> update(UserDTO userDTO);

    ResponseEntity<BaseResponse> checkToken();

    ResponseEntity<BaseResponse> disableById(Long id);

    ResponseEntity<BaseResponse> enableById(Long id);

    ResponseEntity<BaseResponse> getCurrentUser();

    ResponseEntity<BaseResponse> updateImageUser(Long id, MultipartFile image);
}
