package com.example.booksearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page; // 패이지 번호
    private int size; // 패이지당 글의 수

    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    // Sort를 전달받아 Pageable 객체를 반환
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page-1, size, sort);
    }

}
