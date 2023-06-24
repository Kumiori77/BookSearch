package com.example.booksearch.service;

import com.example.booksearch.dto.BookSearchDTO;
import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.dto.PageResultDTO;
import com.example.booksearch.entity.BookSearch;
import com.example.booksearch.repository.BookSearchRepository;
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

        Pageable pageable = requestDTO.getPageable(Sort.by("bno").descending());

        Page<BookSearch> result = repository.findAll(pageable);

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
}
