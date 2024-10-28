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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostViewController {
    private final PostViewDAO dao;
    private final LikesDAO lDao;
    private final CommentsDAO cDao;
    private final static List<String> CATEGORIES = List.of("Health", "Travel", "Life", "Cook", "Q&A");

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
        model.addAttribute("categories", CATEGORIES);
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
    public String submitUpdatePost(@ModelAttribute("post") PostsVO vo, RedirectAttributes redirectAttributes) {
        boolean success = dao.updatePost(vo);
        redirectAttributes.addFlashAttribute("updateSuccess", success);
        return "redirect:/posts/view";
    }

    @PostMapping("/delete")
    public String deletePost(@ModelAttribute("post") PostsVO vo, RedirectAttributes redirectAttributes) {
        boolean success = dao.deletePost(vo.getPostno());
        redirectAttributes.addFlashAttribute("deleteSuccess", success);
        return "redirect:/posts/view";
    }

    @GetMapping("/comment/create")
    public String createComment(@ModelAttribute("login")UsersVO vo, @RequestParam("commType")String type, @RequestParam(value = "commNo", required = false) int commNo, Model model) {
        CommentsVO comm = new CommentsVO();
        comm.setCommNo((type.equals("type1") ? 0 : commNo));
        comm.setNName(vo.getNName());
        comm.setUserId(vo.getUserID());
        model.addAttribute("comment", new CommentsVO());
        return "thymeleaf/createComment";
    }

    @PostMapping("/comment/create")
    public String submitComment(@RequestParam("comment") CommentsVO vo, @RequestParam(value = "commNo", defaultValue = "0") int commNo, RedirectAttributes redirectAttributes) {
        boolean success;
        if (commNo == 0) success = cDao.addComment(vo);
        else success = cDao.addComment(vo, commNo);
        redirectAttributes.addFlashAttribute("createCommSuccess", success);
        return "redirect:/posts/view";
    }

    @GetMapping("/comment/update")
    public String updateComment(@RequestParam("comment") CommentsVO vo, Model model) {
        model.addAttribute("comment", vo);
        return "thymeleaf/updateComment";
    }

    @PostMapping("/comment/update")
    public String submitUpdateComment(@RequestParam("comment") CommentsVO vo, RedirectAttributes redirectAttributes) {
        boolean success = cDao.updateComment(vo);
        redirectAttributes.addFlashAttribute("updateCommSuccess", success);
        return "redirect:/posts/view";
    }

    @PostMapping("/comment/delete")
    public String submitDeleteComment(@RequestParam("comment") CommentsVO vo, RedirectAttributes redirectAttributes) {
        boolean success = cDao.deleteComment(vo);
        redirectAttributes.addFlashAttribute("deleteCommSuccess", success);
        return "redirect:/posts/view";
    }


}
