package com.example.booksearch.controller;

import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booksearch")
@Log4j2
@RequiredArgsConstructor
public class BookSearchController {

    private final BookSearchService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/booksearch/list";
    }

    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", service.getList(pageRequestDTO));
    }
}
