package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.PostListDAO;
import com.kh.HoneySpring.dao.PostMakeDAO;
import com.kh.HoneySpring.vo.PostsVO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMakeController {
    private final PostMakeDAO postMakeDAO;
    private final List<String> CATEGORIES;

    public PostMakeController(PostMakeDAO postMakeDAO, PostListDAO postListDAO) {
        CATEGORIES = postListDAO.category();
        this.postMakeDAO = postMakeDAO;
    }

    @GetMapping("/create")
    public String showCreatePostForm(@SessionAttribute("login")UsersVO vo, Model model) {
        if (vo == null) {
            return "redirect:/users/login"; // 로그인 페이지로 리다이렉트
        }
        PostsVO post = new PostsVO();
        post.setAuthor(vo.getNName());
        post.setUserID(vo.getUserID());
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("post", post);
        return "thymeleaf/postMakeCreate";
    }

    @PostMapping("/create")
    public String submitPost(@ModelAttribute("post") PostsVO postsVO, Model model) {
        boolean isSubmitted = postMakeDAO.PostmakeCreate(postsVO);
        if (!isSubmitted) {
            model.addAttribute("error", "유효하지 않은 사용자 ID 또는 카테고리 입니다.");
            model.addAttribute("categories", CATEGORIES);
            return "thymeleaf/postMakeCreate";
        }
        model.addAttribute("isSubmitted", isSubmitted);
        return "thymeleaf/postMakeResult";
    }
}


