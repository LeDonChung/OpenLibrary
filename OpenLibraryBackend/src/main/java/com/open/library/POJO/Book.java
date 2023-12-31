package com.open.library.POJO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @Column(unique = true, nullable = false)
    private String isbn;

    private int numberOfPages;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String bookCover;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double rating;

    private int viewer;

    private Date publishDate;

    private String language;

    private boolean is_activated;

    private boolean is_deleted;

    private String contentPdf;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categories_books", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Collection<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id"))
    private Collection<Author> authors;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    private Publisher publisher;

}
