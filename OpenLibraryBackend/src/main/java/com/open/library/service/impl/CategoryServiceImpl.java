package com.open.library.service.impl;

import com.open.library.POJO.Category;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.CategoryMapper;
import com.open.library.repository.CategoryRepository;
import com.open.library.service.CategoryService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.request.CategoryDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.CategoryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Category> categories = categoryRepository.findAll();
            List<CategoryResponseDTO> results = categories.stream().map((category -> categoryMapper.toResponseDTO(category))).collect(Collectors.toList());
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(results, true, String.valueOf(HttpStatus.OK.value())),
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
    public ResponseEntity<BaseResponse> save(CategoryDTO categoryDTO) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                String message = "";
                Category category = null;
                if (categoryDTO.getId() == null) {
                    category = categoryMapper.toEntity(categoryDTO);
                    category.set_activated(true);
                    category.set_deleted(false);
                    message = "Thêm thể loại thành công.";
                } else {
                    Optional<Category> categoryOld = categoryRepository.findById(categoryDTO.getId());
                    if (!categoryOld.isPresent()) {
                        message = String.format("Thể loại có mã %d không tồn tại.", categoryDTO.getId());
                        return new ResponseEntity<>(
                                OpenLibraryUtils.getResponse(message, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                    category = categoryMapper.toEntity(categoryDTO, categoryOld.get());
                    message = String.format("Cập nhật thể loại có mã %d thành công.", categoryDTO.getId());
                }
                categoryRepository.save(category);
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(message, true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );

            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCESS_DENIED, false, String.valueOf(HttpStatus.UNAUTHORIZED.value())),
                        HttpStatus.UNAUTHORIZED
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
    public ResponseEntity<BaseResponse> enable(Long id) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                Optional<Category> category = categoryRepository.findById(id);
                if (category.isPresent()) {
                    Category categoryEntity = category.get();
                    categoryEntity.set_deleted(false);
                    categoryEntity.set_activated(true);
                    categoryRepository.save(categoryEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Bật thể loại có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Thể loại có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                            HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCESS_DENIED, false, String.valueOf(HttpStatus.UNAUTHORIZED.value())),
                        HttpStatus.UNAUTHORIZED
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
    public ResponseEntity<BaseResponse> disable(Long id) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                Optional<Category> category = categoryRepository.findById(id);
                if (category.isPresent()) {
                    Category categoryEntity = category.get();
                    categoryEntity.set_deleted(true);
                    categoryEntity.set_activated(false);
                    categoryRepository.save(categoryEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa thể loại có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Thể loại có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                            HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCESS_DENIED, false, String.valueOf(HttpStatus.UNAUTHORIZED.value())),
                        HttpStatus.UNAUTHORIZED
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
    public ResponseEntity<BaseResponse> findById(Long id) {
        try {
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(categoryMapper.toResponseDTO(category.get()), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Thể loại có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                        HttpStatus.BAD_REQUEST
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
