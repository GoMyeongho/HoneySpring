package com.kh.HoneySpring.controller;


import com.kh.HoneySpring.dao.LikesDAO;
import com.kh.HoneySpring.dao.PostListDAO;
import com.kh.HoneySpring.vo.LikesVO;
import com.kh.HoneySpring.vo.PostsVO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostListController {
    private final PostListDAO dao;
    private final LikesDAO lDao;
    private final LikesDAO likesDAO;

    public PostListController(PostListDAO dao, LikesDAO lDao, LikesDAO likesDAO) {
        this.dao = dao;
        this.lDao = lDao;
        this.likesDAO = likesDAO;
    }

    @GetMapping("/board")    // http://localhost:8112/posts/list
    public String showBoard(@ModelAttribute("login") UsersVO vo, Model model) {
        List<PostsVO> board = dao.selectPage();
        List<String> searchOptions = List.of("제목", "작성자");
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        String search ="";
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board")
    public String searchBoard(@ModelAttribute("login") UsersVO vo, @ModelAttribute("option") String option, @ModelAttribute("search") String search, Model model) {
        List<String> searchOptions = List.of("제목", "작성자");
        int sel = searchOptions.indexOf(option);
        List<PostsVO> board = dao.selectPage(search,sel);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board")
    public String categoryBoard(@ModelAttribute("login") UsersVO vo, @ModelAttribute("category") String category, Model model) {
        List<String> searchOptions = List.of("제목", "작성자");
        List<PostsVO> board = dao.selectPage(category,3);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }


}