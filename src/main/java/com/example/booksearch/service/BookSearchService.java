package com.example.booksearch.service;

import com.example.booksearch.dto.BookSearchDTO;
import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.dto.PageResultDTO;
import com.example.booksearch.entity.BookSearch;

public interface BookSearchService {

    Long register(BookSearchDTO dto);

    PageResultDTO<BookSearchDTO, BookSearch> getList(PageRequestDTO requestDTO);

    default BookSearch dtoToEntity(BookSearchDTO dto) {
        BookSearch entity = BookSearch.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .publisher(dto.getPublisher())
                .price(dto.getPrice())
                .build();

        return entity;
    }

    default BookSearchDTO entityToDto(BookSearch entity) {
        BookSearchDTO dto = BookSearchDTO.builder()
                .bno(entity.getBno())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .publisher(entity.getPublisher())
                .price(entity.getPrice())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate()).build();
        return dto;
    }
}
