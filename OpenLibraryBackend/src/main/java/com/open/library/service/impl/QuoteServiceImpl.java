package com.open.library.service.impl;

import com.google.api.Http;
import com.open.library.POJO.Book;
import com.open.library.POJO.Category;
import com.open.library.POJO.Quote;
import com.open.library.POJO.User;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.QuoteMapper;
import com.open.library.repository.QuoteRepository;
import com.open.library.repository.UserRepository;
import com.open.library.service.QuoteService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.request.QuoteDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.BookResponseDTO;
import com.open.library.utils.response.QuoteResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Quote> quotes = quoteRepository.findAll();
            List<QuoteResponseDTO> results = quotes.stream().map((quote -> quoteMapper.toResponseDTO(quote))).collect(Collectors.toList());
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
    public ResponseEntity<BaseResponse> save(QuoteDTO quoteDTO) {
        try {
            boolean isAdmin = jwtService.isAdmin();
            if (isAdmin) {
                String message = "";
                Quote quote = null;
                if (quoteDTO.getId() == null) {
                    quote = quoteMapper.toEntity(quoteDTO);
                    quote.set_activated(true);
                    quote.set_deleted(false);
                    message = "Thêm danh ngôn thành công.";
                } else {
                    Optional<Quote> quoteOld = quoteRepository.findById(quoteDTO.getId());
                    if (!quoteOld.isPresent()) {
                        message = String.format("Danh ngôn có mã %d không tồn tại.", quoteDTO.getId());
                        return new ResponseEntity<>(
                                OpenLibraryUtils.getResponse(message, false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                                HttpStatus.BAD_REQUEST
                        );
                    }
                    quote = quoteMapper.toEntity(quoteDTO, quoteOld.get());
                    message = String.format("Cập nhật danh ngôn có mã %d thành công.", quoteDTO.getId());
                }
                quoteRepository.save(quote);
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
                Optional<Quote> quote = quoteRepository.findById(id);
                if (quote.isPresent()) {
                    Quote quoteEntity = quote.get();
                    quoteEntity.set_activated(true);
                    quoteEntity.set_deleted(false);
                    quoteRepository.save(quoteEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Bật danh ngôn có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Danh ngôn có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
                Optional<Quote> quote = quoteRepository.findById(id);
                if (quote.isPresent()) {
                    Quote quoteEntity = quote.get();
                    quoteEntity.set_activated(false);
                    quoteEntity.set_deleted(true);
                    quoteRepository.save(quoteEntity);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Xóa danh ngôn có mã %d thành công.", id), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Danh ngôn có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            Optional<Quote> quote = quoteRepository.findById(id);
            if (quote.isPresent()) {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(quoteMapper.toResponseDTO(quote.get()), true, String.valueOf(HttpStatus.OK.value())),
                        HttpStatus.OK
                );
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(String.format("Danh ngôn có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
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
            List<Quote> quotes = quoteRepository.findAll(pageable).stream().toList();
            List<QuoteResponseDTO> results = quotes.stream().map((quote -> quoteMapper.toResponseDTO(quote))).collect(Collectors.toList());
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(
                            PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) quoteRepository.count())
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
    public ResponseEntity<BaseResponse> likeById(Long id) {
        try {
            boolean isUser = jwtService.isUser();
            if (isUser) {
                User user = userRepository.findByUsername(jwtService.getCurrentUser()).get();
                Optional<Quote> quote = quoteRepository.findById(id);
                if (quote.isPresent()) {
                    Collection<Quote> quotes = user.getQuotes();
                    quotes.add(quote.get());

                    Quote quoteX = quote.get();
                    quoteX.setLikes(quoteX.getLikes() + 1);

                    quoteRepository.save(quoteX);
                    userRepository.save(user);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Đã thêm danh ngôn vào danh sách yêu thích."), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Danh ngôn có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                            HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCESS_DENIED, false, String.valueOf(HttpStatus.UNAUTHORIZED)),
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
    public ResponseEntity<BaseResponse> unlikeById(Long id) {
        try {
            boolean isUser = jwtService.isUser();
            if (isUser) {

                User user = userRepository.findByUsername(jwtService.getCurrentUser()).get();
                Optional<Quote> quote = quoteRepository.findById(id);
                if (quote.isPresent()) {
                    user.getQuotes().remove(quote.get());

                    Quote quoteX = quote.get();
                    quoteX.setLikes(quoteX.getLikes() - 1);

                    quoteRepository.save(quoteX);
                    userRepository.save(user);
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Đã xóa danh ngôn khỏi danh sách yêu thích."), true, String.valueOf(HttpStatus.OK.value())),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            OpenLibraryUtils.getResponse(String.format("Danh ngôn có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())),
                            HttpStatus.BAD_REQUEST
                    );
                }
            } else {
                return new ResponseEntity<>(
                        OpenLibraryUtils.getResponse(SystemConstraints.ACCESS_DENIED, false, String.valueOf(HttpStatus.UNAUTHORIZED)),
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
    public ResponseEntity<BaseResponse> getRand() {
        try {
            Quote quote = quoteRepository.getRand();
            return new ResponseEntity<>(
                    OpenLibraryUtils.getResponse(quoteMapper.toResponseDTO(quote), true, String.valueOf(HttpStatus.OK)),
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
}
