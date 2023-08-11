package com.open.library.service.impl;

import com.open.library.POJO.Category;
import com.open.library.POJO.Feedback;
import com.open.library.constraints.SystemConstraints;
import com.open.library.jwt.JwtService;
import com.open.library.mapper.FeedbackMapper;
import com.open.library.repository.FeedbackRepository;
import com.open.library.service.FeedbackService;
import com.open.library.service.MailService;
import com.open.library.utils.OpenLibraryUtils;
import com.open.library.utils.PageUtils;
import com.open.library.utils.request.FeedbackDTO;
import com.open.library.utils.request.PageDTO;
import com.open.library.utils.response.BaseResponse;
import com.open.library.utils.response.FeedbackResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final MailService mailService;

    @Override
    public ResponseEntity<BaseResponse> findAll() {
        try {
            List<Feedback> feedbacks = feedbackRepository.findAll();
            List<FeedbackResponseDTO> results = feedbacks.stream().map((feedback -> feedbackMapper.toResponseDto(feedback))).collect(Collectors.toList());
            return new ResponseEntity<>(OpenLibraryUtils.getResponse(results, true, String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> getPages(PageDTO pageDTO) {
        try {
            Pageable pageable = PageRequest.of(pageDTO.getPageIndex(), pageDTO.getPageSize());
            List<Feedback> feedbacks = feedbackRepository.findAll(pageable).stream().toList();
            List<FeedbackResponseDTO> results = feedbacks.stream().map((feedback -> feedbackMapper.toResponseDto(feedback))).collect(Collectors.toList());
            return new ResponseEntity<>(OpenLibraryUtils.getResponse(PageUtils.getPage(pageDTO, Arrays.asList(results.toArray()), (int) feedbackRepository.count()), true, String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(OpenLibraryUtils.getResponse(PageUtils.builder().length(0).pageIndex(0).dataSource(new ArrayList<>()).build(), false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> save(FeedbackDTO feedbackDTO) {
        try {
            Feedback feedback = feedbackMapper.toEntity(feedbackDTO);
            feedback.setStatus(false);
            feedbackRepository.save(feedback);

            return new ResponseEntity<>(OpenLibraryUtils.getResponse("Thêm nhận xét thành công.", true, String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> findById(Long id) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findById(id);
            if (feedback.isPresent()) {
                return new ResponseEntity<>(OpenLibraryUtils.getResponse(feedbackMapper.toResponseDto(feedback.get()), true, String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(OpenLibraryUtils.getResponse(String.format("Nhận xét có mã %d không tồn tại.", id), false, String.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<BaseResponse> sendResponseFeedback(Long feedbackId, String content, boolean defaultEmail) {
        try {
            Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
            if (feedback.isPresent()) {

                mailService.sendResponseFeedback(feedback.get(), content, defaultEmail);
                Feedback feedbackUpdate = feedback.get();
                feedbackUpdate.setStatus(true);
                feedbackUpdate.setResponseMessage(content);
                feedbackRepository.save(feedbackUpdate);
                return new ResponseEntity<>(OpenLibraryUtils.getResponse(String.format("Gửi phản hồi nhận xét có mã %s thành công.", feedbackId), true, String.valueOf(HttpStatus.OK.value())), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(OpenLibraryUtils.getResponse(String.format("Nhận xét có mã %d không tồn tại.", feedbackId), false, String.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(OpenLibraryUtils.getResponse(SystemConstraints.SOMETHING_WENT_WRONG, false, String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
