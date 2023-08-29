package com.open.library.controller;

import com.open.library.constraints.SystemConstraints;
import com.open.library.service.UserService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.PageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/getAllUser")
    public ResponseEntity<BaseResponse> getAllUser(@RequestBody PageDTO pageDTO) {
        try {
            return userService.getAllUser(pageDTO);
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
    @GetMapping("/getCurrentUser")
    public ResponseEntity<BaseResponse> getByUsername() {
        try {
            return userService.getCurrentUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/disable/{id}")
    public ResponseEntity<BaseResponse> deleteById(@PathVariable Long id) {
        try {
            return userService.disableById(id);
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
            return userService.enableById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @PostMapping("/update")
    public ResponseEntity<BaseResponse> updateById(@RequestBody UserDTO userDto) {
        try {
            return userService.update(userDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody UserDTO user) {
        try {
            Map<String, String> errors = ValidateObject.validateUserDTO(user);
            if (!ObjectUtils.isEmpty(errors)) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }


            if (!user.getPassword().equals(user.getConfirmPassword())) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.PASSWORD_NOT_MATCHES, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }

            return userService.register(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @GetMapping("/checkToken")
    public ResponseEntity<BaseResponse> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @PostMapping("/{id}/uploadImage")
    public ResponseEntity<BaseResponse> updateImageUser(@RequestParam("image") MultipartFile image, @PathVariable Long id) {
        try {
            return userService.updateImageUser(id, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @GetMapping("/getAllQuotes")
    public ResponseEntity<BaseResponse> getAllQuotes() {
        try {
            return userService.getAllQuotes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @GetMapping("/existsQuote")
    public ResponseEntity<BaseResponse> existsQuote(@RequestParam Long quoteId) {
        try {
            return userService.existsQuote(quoteId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
