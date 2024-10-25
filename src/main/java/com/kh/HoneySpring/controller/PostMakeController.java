package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.PostMakeDAO;
import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/create")
public class PostMakeController {
    private final PostMakeDAO postMakeDAO;

    public PostMakeController(PostMakeDAO postMakeDAO) { // 의존성 주입
        this.postMakeDAO = postMakeDAO;
    }

    @GetMapping("/create")  // 카테고리를 보여주는 Mapping
    public String shwCateOption(Model model) {  // http://localhost:8112/posts/create
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("postNo", new PostsVO());
        return "Thymeleaf/postMakeCreate";
    }

    @PostMapping("/create") // 글 작성시 DB로 전달하는 PostMapping
    public String makePostIntoDB(@ModelAttribute("postNo") PostsVO postsVO, Model model) {
        boolean isSubmitted = postMakeDAO.PostmakeCreate(postsVO);
        model.addAttribute("isSubmitted", isSubmitted);
        return "Thymeleaf/postMakeResult";
    }
}
