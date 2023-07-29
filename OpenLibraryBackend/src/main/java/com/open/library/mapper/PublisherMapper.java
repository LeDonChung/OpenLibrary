package com.open.library.mapper;

import com.open.library.POJO.Publisher;
import com.open.library.utils.request.PublisherDTO;
import com.open.library.utils.response.PublisherResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {
    public Publisher toEntity(PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setStory(dto.getStory());
        publisher.setAddress(dto.getAddress());
        return publisher;
    }
    public PublisherResponseDTO toResponseDTO(Publisher publisher) {
        PublisherResponseDTO dto = new PublisherResponseDTO();
        dto.setId(publisher.getId());
        dto.setName(publisher.getName());
        dto.setAddress(publisher.getAddress());
        dto.setStory(publisher.getStory());
        return dto;
    }

    public Publisher toEntity(PublisherDTO publisherDTO, Publisher publisherOld) {
        publisherOld.setName(publisherDTO.getName());
        publisherOld.setStory(publisherDTO.getStory());
        publisherOld.setAddress(publisherDTO.getAddress());
        publisherOld.set_deleted(publisherOld.is_deleted());
        publisherOld.set_activated(publisherDTO.is_activated());
        return publisherOld;
    }
}
