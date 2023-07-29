package com.open.library.service.impl;

import com.open.library.POJO.Category;
import com.open.library.POJO.Publisher;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.PublisherMapper;
import com.open.library.repository.PublisherRepository;
import com.open.library.service.PublisherService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.request.PublisherDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.CategoryResponseDTO;
import com.open.library.utils.response.PublisherResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    private final PublisherRepository publisherRepository;
    private final JwtService jwtService;
    private final PublisherMapper publisherMapper;
    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Publisher> publishers = publisherRepository.findAll();
            List<PublisherResponseDTO> results = publishers.stream().map((publisher -> publisherMapper.toResponseDTO(publisher))).collect(Collectors.toList());
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
    public ResponseEntity<BaseResponse> save(PublisherDTO publisherDTO) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                String message = "";
                Publisher publisher = null;
                if (publisherDTO.getId() == null) {
                    publisher = publisherMapper.toEntity(publisherDTO);
                    publisher.set_activated(true);
                    publisher.set_deleted(false);
                    message = "Thêm nhà xuất bản thành công.";
                } else {
                    Optional<Publisher> publisherOld = publisherRepository.findById(publisherDTO.getId());
                    if (!publisherOld.isPresent()) {
                        message = String.format("Nhà xuất bản có mã %d không tồn tại.", publisherDTO.getId());
                        return new ResponseEntity<>(
                                OpenLibraryUtils.getResponse(message, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                    publisher = publisherMapper.toEntity(publisherDTO, publisherOld.get());
                    message = String.format("Cập nhật nhà xuất bản có mã %d thành công.", publisherDTO.getId());
                }
                publisherRepository.save(publisher);
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
                Optional<Publisher> publisher = publisherRepository.findById(id);
                if (publisher.isPresent()) {
                    Publisher publisherEntity = publisher.get();
                    publisherEntity.set_deleted(false);
                    publisherEntity.set_activated(true);
                    publisherRepository.save(publisherEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Bật nhà xuất bản có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Nhà xuất bản có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
                Optional<Publisher> publisher = publisherRepository.findById(id);
                if (publisher.isPresent()) {
                    Publisher publisherEntity = publisher.get();
                    publisherEntity.set_deleted(true);
                    publisherEntity.set_activated(false);
                    publisherRepository.save(publisherEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa nhà xuất bản có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Nhà xuất bản có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                Optional<Publisher> publisher = publisherRepository.findById(id);
                if (publisher.isPresent()) {
                    Publisher publisherEntity = publisher.get();
                    publisherEntity.set_deleted(false);
                    publisherEntity.set_activated(true);
                    publisherRepository.save(publisherEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(publisherMapper.toResponseDTO(publisherEntity), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Nhà xuất bản có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
}
