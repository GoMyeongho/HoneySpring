package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
    @Autowired
    private UsersDAO usersDAO;

    // 회원가입 폼 페이지로 이동
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("userVO", new UsersVO());
        return "register";  // register.html
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String registerUser(@ModelAttribute UsersVO userVO, Model model) {
        try {
            usersDAO.joinMember();
            model.addAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류 발생" + e.getMessage());
            return "register";
        }
    }

    // 아이디 찾기 페이지로 이동
    @GetMapping("/findID")
    public String showFindIDPage() {
        return "findID";  // findID.html
    }

    // 아이디 찾기 처리
    @PostMapping("/findID")
    public String findID(@RequestParam("phone") String phone, Model model) {
        try {
            String userID = usersDAO.findID();
            model.addAttribute("userID", userID);
            return "showID";  // showID.html 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류 발생" + e.getMessage());
            return "findID";
        }
    }

    // 비밀번호 찾기 페이지로 이동
    @GetMapping("/findPW")
    public String showFindPWPage() {
        return "findPW";  // findPW.html
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findPW")
    public String findPW(@RequestParam("userID") String userID,
                         @RequestParam("pwKey") String pwKey, Model model) {
        try {
            String userPW = usersDAO.findPW();
            model.addAttribute("userPW", userPW);
            return "showPW";  // showPW.html 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류 발생" + e.getMessage());
            return "findPW";
        }
    }

}
