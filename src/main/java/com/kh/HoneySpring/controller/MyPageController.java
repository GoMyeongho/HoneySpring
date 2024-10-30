package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.MyPageDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class MyPageController {
    private final MyPageDAO myPageDAO;

    public MyPageController(MyPageDAO myPageDAO) {
        this.myPageDAO = myPageDAO;
    }

    // 사용자 목록 조회 페이지
    @GetMapping("/select")
    public String selectViewUser(@SessionAttribute("login") UsersVO vo, Model model) { // http://localhost:8112/posts/select
        UsersVO user = myPageDAO.findUserById(vo.getUserID());
        model.addAttribute("user", user);
        return "thymeleaf/selectInfo";
    }

    // 사용자 업데이트 폼 (GET 요청)
    @GetMapping("/update")
    public String updateUserForm(@SessionAttribute("login") UsersVO vo, Model model) { // http://localhost:8112/posts/update
        UsersVO userToUpdate = myPageDAO.findUserById(vo.getUserID()); // DAO를 통해 직접 조회
        model.addAttribute("user", userToUpdate);
        return "thymeleaf/updateInfo";
    }

    // 사용자 업데이트 처리 (POST 요청)
    @PostMapping("/update")
    public String updateUser(@ModelAttribute UsersVO vo, RedirectAttributes redirectAttributes) {
        try {
            myPageDAO.usersUpdate(vo);  // 업데이트 처리
            redirectAttributes.addFlashAttribute("message", "사용자 정보가 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "사용자 정보 업데이트에 실패했습니다.");
        }
        return "redirect:/users/select"; // 업데이트 완료
    }
}
