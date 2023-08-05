package com.open.library.controller;

import com.open.library.constraints.SystemConstraints;
import com.open.library.service.AuthorService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAll() {
        try {
            return authorService.findAll();
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
            return authorService.getPages(pageDTO);
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
    public ResponseEntity<BaseResponse> insertOne(@RequestParam("image") MultipartFile image, @RequestParam("authorDto") String authorDto) {
        try {
            System.out.println(image);
            System.out.println(authorDto);

            AuthorDTO authorDTO = (AuthorDTO) OpenLibraryUtils.getRequest(authorDto, AuthorDTO.class);
            Map<String, String> errors = ValidateObject.validateAuthorDTO(authorDTO);

            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return authorService.save(image, authorDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateOne(@RequestParam("image") MultipartFile image, @RequestParam("authorDto") String authorDto) {
        try {
            AuthorDTO authorDTO = (AuthorDTO) OpenLibraryUtils.getRequest(authorDto, AuthorDTO.class);
            Map<String, String> errors = ValidateObject.validateAuthorDTO(authorDTO);
            if(!errors.isEmpty()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }
            return authorService.save(image, authorDTO);
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
            return authorService.enable(id);
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
            return authorService.disable(id);
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
            return authorService.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
