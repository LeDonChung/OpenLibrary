package com.open.library.utils.response;

import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class BookResponseDTO {
    private Long id;

    private String title;

    private String isbn;

    private int numberOfPages;

    private String bookCover;

    private String description;

    private double rating;

    private Date publishDate;

    private int viewer;

    private String language;

    private boolean is_activated;

    private boolean is_deleted;

    private String contentPdf;

    private Collection<CategoryResponseDTO> categories;

    private Collection<AuthorResponseDTO> authors;

    private PublisherResponseDTO publisher;
}
