package com.open.library.controller;

import com.open.library.constraints.SystemConstraints;
import com.open.library.service.CategoryService;
import com.open.library.service.PublisherService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.PublisherDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/publisher")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return publisherService.findAll();
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
            return publisherService.getPages(pageDTO);
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
    public ResponseEntity<BaseResponse> insertOne(@RequestBody PublisherDTO publisherDTO) {
        try {
            Map<String, String> errors = ValidateObject.validatePublisherDTO(publisherDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return publisherService.save(publisherDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateOne(@RequestBody PublisherDTO publisherDTO) {
        try {
            Map<String, String> errors = ValidateObject.validatePublisherDTO(publisherDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return publisherService.save(publisherDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/enable/{id}")
    public ResponseEntity<BaseResponse> enableById(@PathVariable Long id) {
        try {
            return publisherService.enable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/disable/{id}")
    public ResponseEntity<BaseResponse> disableById(@PathVariable Long id) {
        try {
            return publisherService.disable(id);
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
            return publisherService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
