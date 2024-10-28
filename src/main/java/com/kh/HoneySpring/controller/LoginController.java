package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.LoginDAO;
import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.vo.PostsVO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/login")
    public String logIn(String userID, Model model) {   // http://localhost:8112/users/login
        UsersVO usersVO = loginDAO.FindByUserID(userID);
        if (usersVO != null) {  // 로그인에 Null 값이 아닌 정상적인 값
            model.addAttribute("User", userID);
            return "main"; // 로그인 후에 메인 페이지로 반환
        } else {
            model.addAttribute("에러", "아이디, 비밀번호가 올바르지 않습니다.");    // 정상적이지 않은 값
            return "login"; // 로그인페이지 유지
        }
    }

    @GetMapping("/findID") // html 에서 버튼 클릭시 아이디찾기 페이지로
    public String findID() {    // http://localhost:8112/findIDForm
        return "Thymeleaf/findIDForm";
    }

    @GetMapping("/findPW") // html 에서 버튼 클릭시 비밀번호 찾기 페이지로
    public String findPW() {    // http://localhost:8112/findPWForm
        return "Thymeleaf/findPWForm";
    }

    @GetMapping("/signUp") // html 에서 버튼 클릭시 회원가입 페이지로
    public String signUp() {    // http://localhost:8112/findjoinForm
        return "Thymeleaf/joinForm";
    }
}
