package com.example.booksearch.repository;

import com.example.booksearch.dto.BookSearchDTO;
import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.dto.PageResultDTO;
import com.example.booksearch.entity.BookSearch;
import com.example.booksearch.entity.QBookSearch;
import com.example.booksearch.service.BookSearchService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BookSearchRepositoryTest {

    @Autowired
    BookSearchRepository repository;

    // service 인터페이스 참조
    @Autowired
    private BookSearchService service;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            BookSearch bookSearch = BookSearch.builder()
                    .title("Title.."+i)
                    .author("User"+(i%10))
                    .publisher("PUB"+(i%5))
                    .price((10000L + ((i%10) * 1000L))).build();

            System.out.println(repository.save(bookSearch));
        });
    }
    @Test
    public void changeEntity() {

        Optional<BookSearch> result = repository.findById(15L);

        if (result.isPresent()) {
            BookSearch bookSearch = result.get();

            bookSearch.changeTitle("Changed Title...");
            bookSearch.changePrice(20000L);

            repository.save(bookSearch);
        }

    }

    @Test
    public void querydslTest1() {
        // 패이징 처리를 위한 Pageable 객체
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        String keyword = "3";

        QBookSearch qBookSearch = QBookSearch.bookSearch;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qBookSearch.title.contains(keyword);
        builder.and(expression);

        Page<BookSearch> result = repository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testRegister() {
        BookSearchDTO dto = BookSearchDTO.builder()
                .title("Sample Title...")
                .author("Athor : sample")
                .publisher("Publisher : PUB1")
                .price((21000L)).build();
        // service 인터페이스의 register() 메소드 호출해서 dto 객체를 엔티티 객체로 변환
        System.out.println(service.register(dto));
    }


    @Test
    public void  testList() {
        PageRequestDTO requestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10).build();

        PageResultDTO<BookSearchDTO, BookSearch> resultDTO = service.getList(requestDTO);

        for (BookSearchDTO dto : resultDTO.getDtoList()) {
            System.out.println(dto);
        }
    }


}
