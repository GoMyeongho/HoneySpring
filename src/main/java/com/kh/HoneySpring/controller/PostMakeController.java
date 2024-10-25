package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.PostMakeDAO;
import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostMakeController {
    private final PostMakeDAO postMakeDAO;

    public PostMakeController(PostMakeDAO postMakeDAO) { // 의존성 주입
        this.postMakeDAO = postMakeDAO;
    }
    @GetMapping("/insert")
    public String makeViewPost(Model model) {
        model.addAttribute("pos6tNo", new PostsVO());
        return "Thymeleaf/PostInsert";
    }
}
