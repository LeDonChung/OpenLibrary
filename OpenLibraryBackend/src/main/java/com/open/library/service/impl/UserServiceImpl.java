package com.open.library.service.impl;

import com.open.library.POJO.Role;
import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.UserMapper;
import com.open.library.repository.RoleRepository;
import com.open.library.repository.UserRepository;
import com.open.library.service.UserService;
import com.open.library.utils.ImageUploadUtils;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.UserDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

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
    private final ImageUploadUtils imageUploadUtils;

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
    public ResponseEntity<BaseResponse> getAllUser(PageDTO pageDTO) {
        List<UserResponseDTO> results = new ArrayList<>();
        try {
            boolean isAdmin = jwtService.isAdmin();
            if(isAdmin) {
                Pageable pageable = PageRequest.of(pageDTO.getPageIndex(), pageDTO.getPageSize());
                Role roleCustomer = roleRepository.findByCode("CUSTOMER");
                results = userRepository.findAllByRolesContains(roleCustomer, pageable).stream().map((user) -> userMapper.toResponseDto(user)).collect(Collectors.toList());

                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(
                                PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) userRepository.countAllUser())
                                , true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(
                                PageUtils.builder().length(0).pageIndex(0)
                                        .dataSource(new ArrayList<>()).build()
                                , false, String.valueOf(HttpStatus.UNAUTHORIZED.value())),
                        HttpStatus.UNAUTHORIZED
                );
            }
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

    @Override
    public ResponseEntity<BaseResponse> getCurrentUser() {
        try {
            String username = jwtService.getCurrentUser();
            Optional<User> user = this.userRepository.findByUsername(username);
            if(!user.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
                );
            }

            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(userMapper.toResponseDto(user.get()), true, String.valueOf(HttpStatus.OK)),
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
    public ResponseEntity<BaseResponse> updateImageUser(Long id, MultipartFile image) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if(!userOptional.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Người dùng có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.NOT_FOUND.value())),
                        HttpStatus.NOT_FOUND
                );
            }
            User user = userOptional.get();
            if(ObjectUtils.isEmpty(image)) {
                user.setImage(null);
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse("Cập nhật hình ảnh không thành công thành công.", false, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                if(!imageUploadUtils.checkExistedImageUser(image)) {
                    imageUploadUtils.uploadImageUser(image);
                }
                user.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                userRepository.save(user);
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse("Cập nhật hình ảnh thành công.", true, String.valueOf(HttpStatus.OK.value())),
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
