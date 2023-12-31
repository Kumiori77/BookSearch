package com.example.booksearch.service;

import com.example.booksearch.dto.BookSearchDTO;
import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.dto.PageResultDTO;
import com.example.booksearch.entity.BookSearch;
import com.example.booksearch.entity.QBookSearch;
import com.example.booksearch.repository.BookSearchRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {

    private final BookSearchRepository repository;

    @Override
    public Long register(BookSearchDTO dto) {
        BookSearch entity = dtoToEntity(dto);

        repository.save(entity); // DB에 저장

        return entity.getBno();
    }

    @Override
    public PageResultDTO<BookSearchDTO, BookSearch> getList(PageRequestDTO requestDTO) {

        Sort sort = getSorting(requestDTO);

        Pageable pageable = requestDTO.getPageable(sort);

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<BookSearch> result = repository.findAll(booleanBuilder, pageable);

        Function<BookSearch, BookSearchDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BookSearchDTO read(Long bno) {
        Optional<BookSearch> result = repository.findById(bno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long bno) {
        repository.deleteById(bno);
    }

    @Override
    public void modify(BookSearchDTO dto) {

        Optional<BookSearch> result = repository.findById(dto.getBno());

        if (result.isPresent()) {
            BookSearch entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeAuthor(dto.getAuthor());
            entity.changePublisher(dto.getPublisher());
            entity.changePrice(dto.getPrice());

            repository.save(entity);
        }

    }

    @Override
    public BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();
        String keyword = pageRequestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QBookSearch qGuestbook = QBookSearch.bookSearch;

        BooleanExpression expression = qGuestbook.bno.gt(0L);  // gno > 0
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0) { // 검색조건을 선택하지 않은 경우
            return booleanBuilder;
        }

        // 검색 조건 작성
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        else if (type.contains("a")) {
            conditionBuilder.or(qGuestbook.author.contains(keyword));
        }
        else if (type.contains("p")) {
            conditionBuilder.or(qGuestbook.publisher.contains(keyword));
        }

        // 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;    }

    @Override
    public Sort getSorting(PageRequestDTO pageRequestDTO) {

        Sort sort;
        String sortingKeyword = pageRequestDTO.getSorting();

        if (sortingKeyword == null) {
            sort = Sort.by("bno").descending();
            return sort;
        }

        if (sortingKeyword.contains("t")) {
            sort = Sort.by("title");
        }
        else if (sortingKeyword.contains("a")) {
            sort = Sort.by("author");
        }
        else if (sortingKeyword.contains("p")) {
            sort = Sort.by("publisher");
        }
        else if (sortingKeyword.contains("r")) {
            sort = Sort.by("price");
        }
        else {
            sort = Sort.by("bno");
        }

        if (sortingKeyword.contains("A")) {
            sort = sort.ascending();
        }
        else {
            sort = sort.descending();
        }


        return sort;
    }


}
