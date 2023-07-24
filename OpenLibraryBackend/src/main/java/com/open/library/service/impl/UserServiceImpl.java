package com.open.library.service.impl;

import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.UserMapper;
import com.open.library.repository.RoleRepository;
import com.open.library.repository.UserRepository;
import com.open.library.service.UserService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<BaseResponse> register(UserDTO userDTO) {
        try {
            Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
            if (user.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCOUNT_ALREADY_EXISTS, false, String.valueOf(HttpStatus.BAD_REQUEST)),
                        HttpStatus.BAD_REQUEST
                );
            }

            User userRegister = userMapper.toEntity(userDTO);
            userRegister.setRoles(Collections.singletonList(roleRepository.findByCode("CUSTOMER")));
            userRegister.setStatus(1);
            userRegister.setPassword(passwordEncoder.encode(userRegister.getPassword()));
            userRepository.save(userRegister);

            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(SystemConstraints.REGISTER_ACCOUNT_SUCCESS, true, String.valueOf(HttpStatus.OK)),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    public ResponseEntity<BaseResponse> getAllUser() {
        List<UserResponseDTO> results = new ArrayList<>();
        try {
            boolean isAdmin = jwtService.idAdmin();
            if(isAdmin) {
                results = userRepository.findAllUser()
                        .stream().map((user) -> userMapper.toResponseDto(user)).collect(Collectors.toList());

                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(results, true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(results, false, String.valueOf(HttpStatus.UNAUTHORIZED.value())),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(results, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    public ResponseEntity<BaseResponse> update(UserDTO userDTO) {
        try {
            Optional<User> user = userRepository.findById(userDTO.getId());
            if(!user.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Người dùng có mã %d không tồn tại.", userDTO.getId()), false, String.valueOf(HttpStatus.NOT_FOUND.value())),
                        HttpStatus.NOT_FOUND
                );
            } else {
                User userNew = userMapper.toEntity(user.get(), userDTO);
                userRepository.save(userNew);

                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Cập nhật người dùng có mã %d thành công.", userDTO.getId()), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    public ResponseEntity<BaseResponse> checkToken() {
        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(HttpStatus.OK.value(), true, String.valueOf(HttpStatus.OK.value())),
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<BaseResponse> disableById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if(!user.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Người dùng có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.NOT_FOUND.value())),
                        HttpStatus.NOT_FOUND
                );
            } else {
                User userNew = user.get();
                userNew.setStatus(0);
                userRepository.save(userNew);

                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Xóa người dùng có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @Override
    public ResponseEntity<BaseResponse> enableById(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if(!user.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Người dùng có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.NOT_FOUND.value())),
                        HttpStatus.NOT_FOUND
                );
            } else {
                User userNew = user.get();
                userNew.setStatus(1);
                userRepository.save(userNew);

                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Bật người dùng có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
