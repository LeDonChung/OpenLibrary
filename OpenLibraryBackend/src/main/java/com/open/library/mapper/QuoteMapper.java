package com.open.library.mapper;

import com.open.library.POJO.Author;
import com.open.library.POJO.Quote;
import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.request.QuoteDTO;
import com.open.library.utils.response.AuthorResponseDTO;
import com.open.library.utils.response.QuoteResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class QuoteMapper {
    public Quote toEntity(QuoteDTO dto) {
        Quote entity = new Quote();
        entity.setContent(dto.getContent());
        return entity;
    }
    public QuoteResponseDTO toResponseDTO(Quote entity) {
        QuoteResponseDTO dto = new QuoteResponseDTO();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setLikes(entity.getLikes());
        dto.set_deleted(entity.is_deleted());
        dto.set_activated(entity.is_activated());
        return dto;
    }

    public Quote toEntity(QuoteDTO quoteDTO, Quote quoteOld) {
        quoteOld.setContent(quoteDTO.getContent());
        return quoteOld;
    }
}
