package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.CommentsDAO;
import com.kh.HoneySpring.dao.LikesDAO;
import com.kh.HoneySpring.dao.PostViewDAO;
import com.kh.HoneySpring.vo.CommentsVO;
import com.kh.HoneySpring.vo.LikesVO;
import com.kh.HoneySpring.vo.PostsVO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class PostViewController {
    private final PostViewDAO dao;
    private final LikesDAO lDao;
    private final CommentsDAO cDao;

    public PostViewController(PostViewDAO dao , LikesDAO ldao , CommentsDAO cdao) {
        this.dao = dao;
        this.lDao = ldao;
        this.cDao = cdao;
    }

    @GetMapping("/view")    // http://localhost:8112/posts/list
    public String viewPost(@ModelAttribute("login")UsersVO vo, @RequestParam("postno") int postNo, Model model) {
        PostsVO post= dao.viewPost(postNo);
        List<LikesVO> lList = lDao.likeList(vo.getUserID());
        int likeNo = lDao.likeList(postNo).size();
        String likeMark = lDao.likeMark(lList,postNo);
        boolean isPoster = post.getAuthor().equals(vo.getNName());
        List<CommentsVO> cList = cDao.commList(postNo);
        model.addAttribute("post", post);
        model.addAttribute("likeNo",likeNo);
        model.addAttribute("likeMark",likeMark);
        model.addAttribute("cList", cList);
        model.addAttribute("isPoster", isPoster);
        return "thymeleaf/viewPost";
    }

    @GetMapping("/update")
    public String updatePost(@RequestParam("post") PostsVO vo, Model model) {
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("post", vo);
        return "thymeleaf/updatePost";
    }

    @PostMapping("/update")
    public String submitUpdatePost(@ModelAttribute("post") PostsVO vo, Model model) {
        boolean success = dao.updatePost(vo);
        model.addAttribute("success", success);
        return "thymeleaf/submitUpdatePost";
    }

    @PostMapping("/view")
    public String submitDeletePost(@ModelAttribute("post") PostsVO vo, Model model) {
        boolean success = dao.deletePost(vo.getPostno());
        model.addAttribute("success", success);
        return "thymeleaf/submitDeletePost";
    }


}
