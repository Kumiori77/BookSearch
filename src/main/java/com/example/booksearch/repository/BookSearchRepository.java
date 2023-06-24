package com.example.booksearch.repository;

import com.example.booksearch.entity.BookSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BookSearchRepository extends JpaRepository<BookSearch, Long>,
        QuerydslPredicateExecutor<BookSearch> {
}
