package com.open.library.controller;

import com.google.api.Http;
import com.open.library.constraints.SystemConstraints;
import com.open.library.service.QuoteService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.QuoteDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("quote")
@RequiredArgsConstructor
public class QuoteController {
    private final QuoteService quoteService;
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return quoteService.findAll();
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
            return quoteService.getPages(pageDTO);
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
    public ResponseEntity<BaseResponse> insertOne(@RequestBody QuoteDTO quoteDTO) {
        try {
            Map<String, String> errors = ValidateObject.validateQuoteDTO(quoteDTO);

            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return quoteService.save(quoteDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateOne(@RequestBody QuoteDTO quoteDTO) {
        try {
            Map<String, String> errors = ValidateObject.validateQuoteDTO(quoteDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return quoteService.save(quoteDTO);
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
            return quoteService.enable(id);
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
            return quoteService.disable(id);
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
            return quoteService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    @PostMapping("/like/{id}")
    public ResponseEntity<BaseResponse> likeById(@PathVariable Long id) {
        try {
          return quoteService.likeById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/unlike/{id}")
    public ResponseEntity<BaseResponse> unlikeById(@PathVariable Long id) {
        try {
            return quoteService.unlikeById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @GetMapping("/getRand")
    public ResponseEntity<BaseResponse> getRandom() {
        try {
            return quoteService.getRand();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR)),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
