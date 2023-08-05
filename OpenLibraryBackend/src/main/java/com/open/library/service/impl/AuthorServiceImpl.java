package com.open.library.service.impl;

import com.open.library.POJO.Author;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.AuthorMapper;
import com.open.library.repository.AuthorRepository;
import com.open.library.service.AuthorService;
import com.open.library.utils.ImageUploadUtils;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.AuthorResponseDTO;
import com.open.library.utils.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final JwtService jwtService;
    private final AuthorMapper authorMapper;
    private final ImageUploadUtils imageUploadUtils;

    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Author> authors = authorRepository.findAll();
            List<AuthorResponseDTO> results = authors.stream().map((author -> authorMapper.toResponseDTO(author))).collect(Collectors.toList());
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
    public ResponseEntity<BaseResponse> save(MultipartFile image, AuthorDTO authorDTO) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                String message = "";
                Author author = null;
                if (authorDTO.getId() == null) {
                    author = authorMapper.toEntity(authorDTO);
                    author.set_activated(true);
                    author.set_deleted(false);
                    message = "Thêm tác giả thành công.";
                } else {
                    Optional<Author> authorOld = authorRepository.findById(authorDTO.getId());
                    if (!authorOld.isPresent()) {
                        message = String.format("Tác giả có mã %d không tồn tại.", authorDTO.getId());
                        return new ResponseEntity<>(
                                OpenLibraryUtils.getResponse(message, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                    author = authorMapper.toEntity(authorDTO, authorOld.get());
                    message = String.format("Cập nhật tác giả có mã %d thành công.", authorDTO.getId());
                }
                // upload image
                if (ObjectUtils.isEmpty(image)) {
                    if (authorDTO.getId() == null) {
                        author.setImage(null);
                    }
                } else {
                    // check existed
                    if (!imageUploadUtils.checkExistedImageAuthor(image)) {
                        imageUploadUtils.uploadImageAuthor(image);
                    }
                    author.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
                }
                authorRepository.save(author);
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
    public ResponseEntity<BaseResponse> uploadImage(MultipartFile image, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> enable(Long id) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                Optional<Author> author = authorRepository.findById(id);
                if (author.isPresent()) {
                    Author authorEntity = author.get();
                    authorEntity.set_deleted(false);
                    authorEntity.set_activated(true);
                    authorRepository.save(authorEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Bật tác giả có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Tác giả có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
                Optional<Author> author = authorRepository.findById(id);
                if (author.isPresent()) {
                    Author authorEntity = author.get();
                    authorEntity.set_deleted(true);
                    authorEntity.set_activated(false);
                    authorRepository.save(authorEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa tác giả có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Tác giả có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            Optional<Author> author = authorRepository.findById(id);
            if (author.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(authorMapper.toResponseDTO(author.get()), false, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Tác giả có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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

    @Override
    public ResponseEntity<BaseResponse> getPages(PageDTO pageDTO) {
        try {
            Pageable pageable = PageRequest.of(pageDTO.getPageIndex(), pageDTO.getPageSize());
            List<Author> authors = authorRepository.findAll(pageable).stream().toList();
            List<AuthorResponseDTO> results = authors.stream().map((author -> authorMapper.toResponseDTO(author))).collect(Collectors.toList());
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(
                            PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) authorRepository.count())
                            , true, String.valueOf(HttpStatus.OK.value())),
                    HttpStatus.OK
            );
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
}
