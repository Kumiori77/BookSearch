package com.example.booksearch.repository;

import com.example.booksearch.entity.BookSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSearchRepository extends JpaRepository<BookSearch, Long> {
}
