package com.open.library.service;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.QuoteDTO;
import com.open.library.utils.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface QuoteService {
    ResponseEntity<BaseResponse> findAll();

    ResponseEntity<BaseResponse> save(QuoteDTO quoteDTO);

    ResponseEntity<BaseResponse> enable(Long id);

    ResponseEntity<BaseResponse> disable(Long id);

    ResponseEntity<BaseResponse> findById(Long id);

    ResponseEntity<BaseResponse> getPages(PageDTO pageDTO);

    ResponseEntity<BaseResponse> likeById(Long id);

    ResponseEntity<BaseResponse> unlikeById(Long id);

    ResponseEntity<BaseResponse> getRand();
}
