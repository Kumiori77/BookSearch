package com.example.booksearch.controller;

import com.example.booksearch.dto.BookSearchDTO;
import com.example.booksearch.dto.PageRequestDTO;
import com.example.booksearch.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("register")
    public void register() {

    }

    @PostMapping("register")
    public String registerPost(BookSearchDTO dto, RedirectAttributes redirectAttributes){

        Long bno = service.register(dto);

        redirectAttributes.addFlashAttribute("bno", bno);
        redirectAttributes.addFlashAttribute("title", "Registed new Book!!");
        redirectAttributes.addFlashAttribute("msg", "새로운 책이 등록되었습니다.");

        return "redirect:/booksearch/list";
    }

    @GetMapping({"read", "modify"})
    public void read(Long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        BookSearchDTO dto = service.read(bno);

        model.addAttribute("dto", dto);
    }

    @PostMapping("remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        service.remove(bno);

        redirectAttributes.addFlashAttribute("bno", bno);
        redirectAttributes.addFlashAttribute("title", "Book Removed!");
        redirectAttributes.addFlashAttribute("msg", "책이 삭제되었습니다.");

        return "redirect:/booksearch/list";

    }

    @PostMapping("modify")
    public String modify(BookSearchDTO dto, @ModelAttribute("requestDTO")
    PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        service.modify(dto);

        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("bno", dto.getBno());
        redirectAttributes.addFlashAttribute("modalTitle", "Book Modified!");
        redirectAttributes.addFlashAttribute("modalMsg", "책 정보가 수정되었습니다.");

        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addAttribute("sorting", requestDTO.getSorting());

        return "redirect:/booksearch/read";
    }

}
