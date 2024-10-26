package com.kh.HoneySpring.controller;


import com.kh.HoneySpring.dao.PostListDAO;
import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostListController {
    private final PostListDAO dao;

    public PostListController(PostListDAO dao) {
        this.dao = dao;
    }

    @GetMapping("/board")    // http://localhost:8112/posts/list
    public String showBoard(Model model) {
        List<PostsVO> board = dao.selectPage();
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        List<String> searchOptions = List.of("제목", "내용", "작성자", "제목 + 내용");
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("categories", categories);
        model.addAttribute("board", board);
        return "thymeleaf/showBoard";
    }
}
