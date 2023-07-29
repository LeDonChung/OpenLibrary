package com.open.library.service;

import com.open.library.utils.request.BookDTO;
import com.open.library.utils.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    ResponseEntity<BaseResponse> findAll();

    ResponseEntity<BaseResponse> save(MultipartFile bookCover, BookDTO bookDTO);

    ResponseEntity<BaseResponse> enable(Long id);

    ResponseEntity<BaseResponse> disable(Long id);

    ResponseEntity<BaseResponse> findById(Long id);
}
