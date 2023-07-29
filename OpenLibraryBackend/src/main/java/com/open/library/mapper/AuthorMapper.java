package com.open.library.mapper;

import com.open.library.POJO.Author;
import com.open.library.utils.request.AuthorDTO;
import com.open.library.utils.response.AuthorResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author toEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setFullName(dto.getFullName());
        author.setStory(dto.getStory());
        return author;
    }
    public AuthorResponseDTO toResponseDTO(Author author) {
        AuthorResponseDTO dto = new AuthorResponseDTO();
        dto.setId(author.getId());
        dto.setFullName(author.getFullName());
        dto.setStory(author.getStory());
        dto.setImage(author.getImage());
        dto.set_activated(author.is_activated());
        dto.set_deleted(author.is_deleted());
        return dto;
    }

    public Author toEntity(AuthorDTO authorDto, Author authorOld) {
        authorOld.setFullName(authorDto.getFullName());
        authorOld.setStory(authorDto.getStory());
        return authorOld;
    }
}
