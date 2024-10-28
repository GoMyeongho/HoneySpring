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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostListController {
    private final PostListDAO dao;
    private final LikesDAO lDao;
    private final static List<String> searchOptions = List.of("제목", "작성자");
    private final static List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");

    public PostListController(PostListDAO dao, LikesDAO lDao) {
        this.dao = dao;
        this.lDao = lDao;
    }

    @GetMapping("/board")    // http://localhost:8112/posts/list
    public String showBoard(@ModelAttribute("login") UsersVO vo, Model model) {
        List<PostsVO> board = dao.selectPage();
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        String search ="";
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board/search")
    public String searchBoard(@ModelAttribute("login") UsersVO vo, @RequestParam("searchOptions") String option, @RequestParam("search") String search, Model model) {
        int sel = searchOptions.indexOf(option);
        List<PostsVO> board = dao.selectPage(search,sel);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("searchContent",search);
        model.addAttribute("search","");
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board/category")
    public String categoryBoard(@ModelAttribute("login") UsersVO vo, @RequestParam("category") String category, Model model) {
        List<PostsVO> board = dao.selectPage(category,3);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board/user")
    public String userBoard(@ModelAttribute("login") UsersVO vo, @RequestParam("name") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,2);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }

    @GetMapping("/board/like")
    public String likeBoard(@ModelAttribute("login") UsersVO vo, @RequestParam("likeName") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,4);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }

    @GetMapping("/board/comment")
    public String commentBoard(@ModelAttribute("login") UsersVO vo, @RequestParam("comment") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,5);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", board.size()/10);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        return "thymeleaf/showBoard";
    }

}