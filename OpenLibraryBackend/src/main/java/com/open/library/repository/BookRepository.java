package com.open.library.repository;

import com.google.cloud.firestore.Query;
import com.open.library.POJO.Book;
import com.open.library.POJO.Category;
import com.open.library.utils.request.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByCategoriesCode(String code, Pageable pageable);

    Page<Book> findByAuthorsId(Long id, Pageable pageable);
    Page<Book> findByPublisherId(Long id, Pageable pageable);
}
