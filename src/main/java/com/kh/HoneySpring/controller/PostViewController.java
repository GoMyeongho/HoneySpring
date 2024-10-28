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

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/board")
public class PostViewController {
    private final PostViewDAO dao;
    private final LikesDAO lDao;
    private final CommentsDAO cDao;
    private final static List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");

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
        List<CommentsVO> cList = cDao.commList(postNo);
        Collections.sort(cList);
        for (int i = 1; i < cList.size(); i++) {
            if (cList.get(i).getCommNo() == cList.get(i - 1).getCommNo()) {
                cList.get(i).setContent("->" + cList.get(i).getContent());
            }
        }
        model.addAttribute("post", post);
        model.addAttribute("likeNo",likeNo);
        model.addAttribute("likeMark",likeMark);
        model.addAttribute("cList", cList);
        model.addAttribute("name", vo.getNName());
        model.addAttribute("categories", categories);
        return "thymeleaf/viewPost";
    }

    @GetMapping("view/update")
    public String updatePost(@RequestParam("post") PostsVO vo, Model model) {
        List<String> categories = List.of("Health", "Travel", "Life", "Cook", "Q&A");
        model.addAttribute("categories", categories);
        model.addAttribute("post", vo);
        return "thymeleaf/updatePost";
    }

    @PostMapping("view/update")
    public String submitUpdatePost(@ModelAttribute("post") PostsVO vo, Model model) {
        boolean success = dao.updatePost(vo);
        model.addAttribute("success", success);
        return "thymeleaf/submitUpdatePost";
    }

    @PostMapping("/view/delete")
    public String submitDeletePost(@ModelAttribute("post") PostsVO vo, Model model) {
        boolean success = dao.deletePost(vo.getPostno());
        model.addAttribute("success", success);
        return "thymeleaf/submitDeletePost";
    }


}
