package com.open.library.controller;

import com.open.library.constraints.SystemConstraints;
import com.open.library.service.FeedbackService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.FeedbackDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return feedbackService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/getPages")
    public ResponseEntity<BaseResponse> getPages(@RequestBody PageDTO pageDTO) {
        try {
            return feedbackService.getPages(pageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(
                        PageUtils.builder().length(0).pageIndex(0)
                                .dataSource(new ArrayList<>()).build()
                        , false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/insert")
    public ResponseEntity<BaseResponse> insertOne(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            Map<String, String> errors = ValidateObject.validateFeedbackDTO(feedbackDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return feedbackService.save(feedbackDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable Long id) {
        try {
            return feedbackService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping("/sendResponseFeedback/{id}")
    public ResponseEntity<BaseResponse> sendResponseFeedback(@PathVariable Long id, @RequestParam(required = false) String content, @RequestParam(defaultValue = "true") boolean defaultFeedback) {
        try {
            return feedbackService.sendResponseFeedback(id, content, defaultFeedback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
