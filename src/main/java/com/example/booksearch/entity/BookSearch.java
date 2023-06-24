package com.example.booksearch.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookSearch extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String author;

    @Column(length = 50, nullable = false)
    private String publisher;

    @Column(nullable = false)
    private Long price;

    // setter

    public void changeTitle(String title) {
        this.title = title;
    }
    public void changePrice(Long price) {this.price = price; }
    public void changeAuthor(String author) { this.author = author;}
    public void changePublisher(String publisher) { this.publisher = publisher;}

}
