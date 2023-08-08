package com.open.library.service;


import com.open.library.utils.request.FeedbackDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.PublisherDTO;
import com.open.library.utils.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<BaseResponse> findAll();
    ResponseEntity<BaseResponse> getPages(PageDTO pageDTO);
    ResponseEntity<BaseResponse> save(FeedbackDTO feedbackDTO);
    ResponseEntity<BaseResponse> findById(Long id);
    ResponseEntity<BaseResponse> sendResponseFeedback(Long feedbackId, String content, boolean defaultEmail);

}
