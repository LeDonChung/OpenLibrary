package com.open.library.controller;

import com.open.library.constraints.SystemConstraints;
import com.open.library.service.UserService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.ValidateObject;
import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAllUser")
    public ResponseEntity<BaseResponse> getAllUser() {
        try {
            return userService.getAllUser();
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
            Map<String, String> errors = ValidateObject.validateUserDTO(userDto);
            if (ObjectUtils.isEmpty(errors)) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(errors, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }

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
}
