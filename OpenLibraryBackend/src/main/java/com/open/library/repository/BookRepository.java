package com.open.library.repository;

import com.open.library.POJO.Book;
import com.open.library.utils.request.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
