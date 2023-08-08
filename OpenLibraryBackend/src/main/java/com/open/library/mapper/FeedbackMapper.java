package com.open.library.mapper;


import com.open.library.POJO.Feedback;
import com.open.library.utils.request.FeedbackDTO;
import com.open.library.utils.response.FeedbackResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public Feedback toEntity(FeedbackDTO dto) {
        Feedback entity = new Feedback();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setMessage(dto.getMessage());
        return entity;
    }
    public FeedbackResponseDTO toResponseDto(Feedback entity) {
        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        return dto;
    }

}
