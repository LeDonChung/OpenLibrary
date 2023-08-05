package com.open.library.service;

import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<BaseResponse> findAll();
    ResponseEntity<BaseResponse> save(CategoryDTO categoryDTO);
    ResponseEntity<BaseResponse> enable(Long id);
    ResponseEntity<BaseResponse> disable(Long id);
    ResponseEntity<BaseResponse> findById(Long id);

    ResponseEntity<BaseResponse> getPages(PageDTO pageDTO);
}
