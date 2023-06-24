package com.example.booksearch.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/booksearch")
@Log4j2
public class BookSearchController {

    @GetMapping({"/", "/list"})
    public String list() {
        return "/booksearch/list";
    }
}
