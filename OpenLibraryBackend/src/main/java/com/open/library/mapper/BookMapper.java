package com.open.library.mapper;

import com.open.library.POJO.Book;
import com.open.library.repository.AuthorRepository;
import com.open.library.repository.CategoryRepository;
import com.open.library.repository.PublisherRepository;
import com.open.library.utils.request.BookDTO;
import com.open.library.utils.response.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {
    private final CategoryMapper categoryMapper;
    private final PublisherMapper publisherMapper;
    private final AuthorMapper authorMapper;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    public Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setNumberOfPages(dto.getNumberOfPages());
        book.setDescription(dto.getDescription());
        book.setPublishDate(dto.getPublishDate());
        book.setLanguage(dto.getLanguage());
        book.setAuthors(authorRepository.findAllById(dto.getAuthors()));
        book.setCategories(categoryRepository.findAllById(dto.getCategories()));
        book.setPublisher(publisherRepository.findById(dto.getPublisherId()).get());
        return book;
    }

    public BookResponseDTO toResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setNumberOfPages(book.getNumberOfPages());
        dto.setBookCover(book.getBookCover());
        dto.setDescription(book.getDescription());
        dto.setRating(book.getRating());
        dto.setPublishDate(book.getPublishDate());
        dto.setLanguage(book.getLanguage());
        dto.set_activated(book.is_activated());
        dto.set_deleted(book.is_deleted());
        if (book.getPublisher() != null) {
            dto.setPublisher(publisherMapper.toResponseDTO(book.getPublisher()));
        }
        if (book.getAuthors() != null) {
            dto.setAuthors(book.getAuthors().stream().map((author -> authorMapper.toResponseDTO(author))).collect(Collectors.toList()));
        }
        if (book.getCategories() != null) {
            dto.setCategories(book.getCategories().stream().map((category -> categoryMapper.toResponseDTO(category))).collect(Collectors.toList()));
        }
        return dto;
    }

    public Book toEntity(BookDTO bookDTO, Book bookOld) {
        bookOld.setTitle(bookDTO.getTitle());
        bookOld.setIsbn(bookDTO.getIsbn());
        bookOld.setNumberOfPages(bookDTO.getNumberOfPages());
        bookOld.setDescription(bookDTO.getDescription());
        bookOld.setPublishDate(bookDTO.getPublishDate());
        bookOld.setLanguage(bookDTO.getLanguage());
        bookOld.setAuthors(authorRepository.findAllById(bookDTO.getAuthors()));
        bookOld.setCategories(categoryRepository.findAllById(bookDTO.getCategories()));
        bookOld.setPublisher(publisherRepository.findById(bookDTO.getPublisherId()).get());
        return bookOld;
    }
}
