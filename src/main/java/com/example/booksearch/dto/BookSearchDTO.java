package com.example.booksearch.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookSearchDTO {

    private Long bno;
    private String title;
    private String author;
    private String publisher;
    private Long price;
    private LocalDateTime regDate, modDate;
}
