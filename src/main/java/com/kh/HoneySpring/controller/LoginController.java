package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.LoginDAO;
import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.vo.PostsVO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class LoginController {
    private final LoginDAO loginDAO;

    public LoginController(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @PostMapping("/login")  // http://localhost:8112/users/login
    public String login(@ModelAttribute("login") UsersVO usersVO, Model model) {
    UsersVO dbBasedUser = loginDAO.FindByUserID(usersVO.getUserID());
        if (dbBasedUser != null && usersVO.getUserPW().equals(usersVO.getUserPW())) {
            model.addAttribute("userID", usersVO.getUserID());
            return "Thymeleaf/showBoard"; // 로그인 후에 포스트 페이지로 전달
        } else {
            model.addAttribute("login", new UsersVO());
            model.addAttribute("에러", "아이디, 비밀번호가 올바르지 않습니다.");
            return "Thymeleaf/login"; // 로그인 페이지 유지
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new UsersVO());
        return "Thymeleaf/login";
    }

    @GetMapping("/findID") // html 에서 버튼 클릭시 아이디찾기 페이지로
    public String findID(Model model) {    // http://localhost:8112/findID
        return "Thymeleaf/findID";
    }

    @GetMapping("/findPW") // html 에서 버튼 클릭시 비밀번호 찾기 페이지로
    public String findPW(Model model) {    // http://localhost:8112/findPW
        return "Thymeleaf/findPW";
    }

    @GetMapping("/signUp") // html 에서 버튼 클릭시 회원가입 페이지로
    public String signUp(Model model) {    // http://localhost:8112/joinUser
        return "Thymeleaf/joinUser";
    }
}
