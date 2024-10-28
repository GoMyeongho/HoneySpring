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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostListController {
    private final PostListDAO dao;
    private final LikesDAO lDao;
    private final static List<String> SEARCHOPTIONS = List.of("제목", "작성자");
    private final static List<String> CATEGORIES = List.of("Health", "Travel", "Life", "Cook", "Q&A");
    private final static int MAXBOARD = 10;

    public PostListController(PostListDAO dao, LikesDAO lDao) {
        this.dao = dao;
        this.lDao = lDao;
    }
// 현재는 검색을 DB로 수행하고 있지만 나중에 기능 추가로 페이지별로 따로 검색할 수 있게 만들기
    @GetMapping("/board")    // http://localhost:8112/posts/list
    public String showBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "type", defaultValue = "type1") String type,
                            @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "search", required = false) String search,
                            Model model) {
        List<PostsVO> board = new ArrayList<>();

        switch (type) {
            case "type1":
                // 전체 검색
                board = dao.selectPage();
                break;
            case "type2":
                // 닉네임(포함) 검색
                board = dao.selectPage(search,0);
                break;
            case "type3":
                // 제목 검색
                board = dao.selectPage(search,1);
                break;
            case "type4":
                // 닉네임(일치) 검색
                board = dao.selectPage(search,2);
                break;
            case "type5":
                // 카테고리 검색
                board = dao.selectPage(search,3);
                break;
            case "type6":
                // 좋아요 검색
                board = dao.selectPage(search,4);
                break;
            case "type7":
                // 댓글 검색
                board = dao.selectPage(search,5);
                break;
            default:System.out.println("코드 에러");
                break;
        }
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        int boardNo = (int)Math.ceil((double)board.size()/MAXBOARD);
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", CATEGORIES);
        model.addAttribute("maxBoard",MAXBOARD);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", SEARCHOPTIONS);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        return "thymeleaf/showBoard";
    }
        /*
    @GetMapping("/board")
    public String searchBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("searchOptions") String option, @RequestParam("search") String search, Model model) {
        int sel = searchOptions.indexOf(option);
        List<PostsVO> board = dao.selectPage(search,sel);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        int boardNo = board.size()/10 + 1;
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("searchContent",search);
        model.addAttribute("search","");
        model.addAttribute("page", page);
        return "thymeleaf/showBoard";
    }


    @GetMapping("/board")
    public String categoryBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("category") String category, Model model) {
        List<PostsVO> board = dao.selectPage(category,3);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        int boardNo = board.size()/10 + 1;
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        model.addAttribute("page", page);
        return "thymeleaf/showBoard";
    }
    @GetMapping("/board")
    public String userBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("name") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,2);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        int boardNo = board.size()/10 + 1;
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        model.addAttribute("page", page);
        return "thymeleaf/showBoard";
    }

    @GetMapping("/board")
    public String likeBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("likeName") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,4);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        int boardNo = board.size()/10 + 1;
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        model.addAttribute("page", page);
        return "thymeleaf/showBoard";
    }

    @GetMapping("/board")
    public String commentBoard(@ModelAttribute("login") UsersVO vo, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam("comment") String name, Model model) {
        List<PostsVO> board = dao.selectPage(name,5);
        String id = vo.getUserID();
        List<LikesVO> like = lDao.likeList(id);
        String search ="";
        int boardNo = board.size()/10 + 1;
        for (PostsVO post : board) post.setTitle(post.getTitle() + "[" + lDao.likeMark(like, post.getPostno()) + "]");
        model.addAttribute("categories", categories);
        model.addAttribute("maxBoard",10);
        model.addAttribute("boardNo", boardNo);
        model.addAttribute("searchOptions", searchOptions);
        model.addAttribute("board", board);
        model.addAttribute("search",search);
        model.addAttribute("page", page);
        return "thymeleaf/showBoard";
    }
     */

}