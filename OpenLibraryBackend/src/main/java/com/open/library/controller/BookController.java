package com.open.library.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.library.POJO.Book;
import com.open.library.constraints.SystemConstraints;
import com.open.library.service.BookService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.BookDTO;
import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return bookService.findAll();
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
            return bookService.getPages(pageDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/getPagesByTypeAndValue")
    public ResponseEntity<BaseResponse> getPages(@RequestBody PageDTO pageDTO, @RequestParam String type, @RequestParam String value) {
        try {
            return bookService.getPagesByTypeAndValue(pageDTO, type, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/insert")
    public ResponseEntity<BaseResponse> insertOne(@RequestParam(value = "contentPdf", required = false) MultipartFile contentPdf, @RequestParam(value = "bookCover", required = false) MultipartFile bookCover, @RequestParam("bookDto") String bookDto) {
        try {
            BookDTO bookDTO = (BookDTO) OpenLibraryUtils.getRequest(bookDto, BookDTO.class);
            Map<String, String> errors = ValidateObject.validateBookDTO(bookDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return bookService.save(contentPdf, bookCover, bookDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping("/update")
    public ResponseEntity<BaseResponse> update(@RequestParam(value = "contentPdf", required = false) MultipartFile contentPdf, @RequestParam(value = "bookCover", required = false) MultipartFile bookCover, @RequestParam("bookDto") String bookDto) {
        try {
            BookDTO bookDTO = (BookDTO) OpenLibraryUtils.getRequest(bookDto, BookDTO.class);
            Map<String, String> errors = ValidateObject.validateBookDTO(bookDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return bookService.save(contentPdf, bookCover, bookDTO);
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
            return bookService.enable(id);
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
            return bookService.disable(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<BaseResponse> findById(@PathVariable Long id) {
        try {
            return bookService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/delete/{id}")
    public ResponseEntity<BaseResponse> removeForever(@PathVariable Long id) {
        try {
            return bookService.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
