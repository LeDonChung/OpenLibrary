package com.open.library.repository;

import com.open.library.POJO.Book;
import com.open.library.POJO.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCode(String code);

    @Query(value = "select c from Category c where c.is_activated = true and c.is_deleted = false")
    List<Category> findAllByActivated();

}
