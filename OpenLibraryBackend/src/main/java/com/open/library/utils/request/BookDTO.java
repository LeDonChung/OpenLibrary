package com.open.library.utils.request;

import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class BookDTO {
    private Long id;

    private String title;

    private String isbn;

    private int numberOfPages;

    private String bookCover;

    private String description;

    private double rating;

    private Date publishDate;

    private String language;

    private Collection<Long> categories;

    private Collection<Long> authors;

    private Long publisher;

    private String contentPdf;

    private boolean is_activated;

    private boolean is_deleted;
}
