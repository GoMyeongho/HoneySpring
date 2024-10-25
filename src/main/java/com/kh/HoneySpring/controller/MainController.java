package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.dao.findIDDAO;
import com.kh.HoneySpring.dao.findPWDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired
    private UsersDAO usersDAO;
    private findIDDAO findIDDAO;
    private findPWDAO findPWDAO;

    // 메인 페이지
    @GetMapping("/main")
    public String mainPage() {
        return "main";  // main.html로 연결
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // login.html로 연결
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("userID") String userID,
                        @RequestParam("userPW") String userPW, Model model) {
        String nickname = usersDAO.login(userID, userPW);
        if (nickname != null) {
            model.addAttribute("nickname", nickname);
            return "welcome";  // 로그인 성공 시 welcome.html로 이동
        } else {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";  // 로그인 실패 시 다시 login.html로 이동
        }
    }

    // 회원가입 페이지로 이동
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
            String userID = findIDDAO.findID();
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
            String userPW = findPWDAO.findPW();
            model.addAttribute("userPW", userPW);
            return "showPW";  // showPW.html 페이지로 이동
        } catch (Exception e) {
            model.addAttribute("errorMessage", "오류 발생" + e.getMessage());
            return "findPW";
        }
    }
}
