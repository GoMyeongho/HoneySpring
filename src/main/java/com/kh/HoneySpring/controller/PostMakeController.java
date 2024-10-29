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
@RequestMapping("/posts")
public class PostMakeController {
    private final PostMakeDAO postMakeDAO;

    public PostMakeController(PostMakeDAO postMakeDAO) {
        this.postMakeDAO = postMakeDAO;
    }

    @GetMapping("/create")
    public String showCreatePostForm(Model model) {
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "QNA");
        model.addAttribute("categories", categories);
        model.addAttribute("post", new PostsVO());
        return "Thymeleaf/postMakeCreate";
    }

    @PostMapping("/create")
    public String submitPost(@ModelAttribute("post") PostsVO postsVO, Model model) {
        boolean isSubmitted = postMakeDAO.PostmakeCreate(postsVO);
        if (!isSubmitted) {
            model.addAttribute("error", "유효하지 않은 사용자 ID 또는 카테고리 입니다.");
            List<String> categories = List.of("Health", "Travel", "Life", "Cook", "QNA");
            model.addAttribute("categories", categories);
            return "Thymeleaf/postMakeCreate";
        }
        model.addAttribute("isSubmitted", isSubmitted);
        return "Thymeleaf/postMakeResult";
    }
}


