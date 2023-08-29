package com.open.library.repository;

import com.open.library.POJO.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @Query(value = "select * from quotes order by RAND() LIMIT 1", nativeQuery = true)
    Quote getRand();
}
