package com.open.library.service.impl;

import com.open.library.POJO.Book;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.BookMapper;
import com.open.library.repository.BookRepository;
import com.open.library.repository.CategoryRepository;
import com.open.library.service.BookService;
import com.open.library.service.FirebaseService;
import com.open.library.utils.ImageUploadUtils;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.request.BookDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ImageUploadUtils imageUploadUtils;
    private final BookMapper bookMapper;
    private final JwtService jwtService;
    private final FirebaseService firebaseService;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Book> books = bookRepository.findAll();
            List<BookResponseDTO> results = books.stream().map((author -> bookMapper.toResponseDTO(author))).collect(Collectors.toList());
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
    public ResponseEntity<BaseResponse> save(MultipartFile contentPdf, MultipartFile bookCover, BookDTO bookDTO) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                String message = "";
                Book book = null;
                if (bookDTO.getId() == null) {
                    book = bookMapper.toEntity(bookDTO);
                    book.setRating(0);
                    book.set_activated(true);
                    book.set_deleted(false);
                    message = "Thêm sách thành công.";
                } else {
                    Optional<Book> bookOld = bookRepository.findById(bookDTO.getId());
                    if (!bookOld.isPresent()) {
                        message = String.format("Sách có mã %d không tồn tại.", bookDTO.getId());
                        return new ResponseEntity<>(
                                OpenLibraryUtils.getResponse(message, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                    book = bookMapper.toEntity(bookDTO, bookOld.get());
                    message = String.format("Cập nhật sách có mã %d thành công.", bookDTO.getId());
                }
                // upload image
                if (ObjectUtils.isEmpty(bookCover)) {
                    if (bookDTO.getId() == null) {
                        book.setBookCover(null);
                    }
                } else {
                    // check existed
                    if (!imageUploadUtils.checkExistedBookCover(bookCover)) {
                        imageUploadUtils.uploadBookCover(bookCover);
                    }
                    book.setBookCover(Base64.getEncoder().encodeToString(bookCover.getBytes()));
                }
                // upload content pdf
                if(ObjectUtils.isEmpty(contentPdf)) {
                    if(bookDTO.getId() == null) {
                        book.setContentPdf("");
                    }
                } else {

                    String fileName = contentPdf.getOriginalFilename();

                    if(bookDTO.getId() == null) {
                        fileName = firebaseService.uploadFile(contentPdf).toString();
                    } else {
                        if(!book.getContentPdf().contains(fileName)) {
                            // check not exist
                            // upload new
                            fileName = firebaseService.uploadFile(contentPdf).toString();
                        } else {
                            // existed
                            // upload update
                            fileName = firebaseService.updateFile(bookDTO.getContentPdf(), contentPdf).toString();
                        }
                    }
                    book.setContentPdf(fileName);
                }
                bookRepository.save(book);
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
                Optional<Book> book = bookRepository.findById(id);
                if (book.isPresent()) {
                    Book bookEntity = book.get();
                    bookEntity.set_deleted(false);
                    bookEntity.set_activated(true);
                    bookRepository.save(bookEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Bật sách có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Sách có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
                Optional<Book> book = bookRepository.findById(id);
                if (book.isPresent()) {
                    Book bookEntity = book.get();
                    bookEntity.set_deleted(true);
                    bookEntity.set_activated(false);
                    bookRepository.save(bookEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa sách có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Sách có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            Optional<Book> book = bookRepository.findById(id);
            if (book.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(bookMapper.toResponseDTO(book.get()), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Sách có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            List<Book> books = bookRepository.findAll(pageable).stream().toList();
            List<BookResponseDTO> results = books.stream().map((book -> bookMapper.toResponseDTO(book))).collect(Collectors.toList());
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(
                            PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) bookRepository.count())
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

    @Override
    public ResponseEntity<BaseResponse> remove(Long id) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                Optional<Book> bookOption = bookRepository.findById(id);
                if(bookOption.isPresent()) {
                    bookRepository.deleteById(id);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa sách có mã %s thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Sách có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
    public ResponseEntity<BaseResponse> getPagesByTypeAndValue(PageDTO pageDTO, String type, String value) {
        try {
            Sort sort  = pageDTO.getSorter().getSortName().equals("asc")
                    ? Sort.by(pageDTO.getSorter().getSortBy()).ascending()
                    : Sort.by(pageDTO.getSorter().getSortBy()).descending();

            Pageable pageable = PageRequest.of(pageDTO.getPageIndex() - 1, pageDTO.getPageSize(), sort);

            List<Book> books = new ArrayList<>();
            if(type.equals(SystemConstraints.CATEGORY)) {
                books = bookRepository.findByCategoriesCode(value, pageable).stream().toList();
            } else if(type.equals(SystemConstraints.AUTHOR)) {
                books = bookRepository.findByAuthorsId(Long.valueOf(value), pageable).stream().toList();
            } else if(type.equals(SystemConstraints.PUBLISHER)) {
                books = bookRepository.findByPublisherId(Long.valueOf(value), pageable).stream().toList();
            }
            List<BookResponseDTO> results = books.stream().map((book -> bookMapper.toResponseDTO(book))).collect(Collectors.toList());
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(
                            PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) bookRepository.count())
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
