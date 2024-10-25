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
public class PostListCotroller {
    private final PostListDAO dao;

    public PostListCotroller(PostListDAO dao) {
        this.dao = dao;
    }

    @GetMapping("/list")    // http://localhost:8112/posts/list
    public String selectPage(int sel, Model model) {
        List<PostsVO> pList =
    }
}
