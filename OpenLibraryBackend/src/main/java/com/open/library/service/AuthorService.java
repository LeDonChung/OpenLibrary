package com.open.library.service;

import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AuthorService {
    ResponseEntity<BaseResponse> findAll();
    ResponseEntity<BaseResponse> save(AuthorDTO authorDTO);
    ResponseEntity<BaseResponse> uploadImage(MultipartFile image, Long id);
    ResponseEntity<BaseResponse> enable(Long id);
    ResponseEntity<BaseResponse> disable(Long id);
    ResponseEntity<BaseResponse> findById(Long id);
}
