package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.dao.findIDDAO;
import com.kh.HoneySpring.dao.findPWDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/users")
public class MainController {

    private final UsersDAO usersDAO = new UsersDAO();
    private com.kh.HoneySpring.dao.findIDDAO findIDDAO;
    private com.kh.HoneySpring.dao.findPWDAO findPWDAO;

    // 회원가입 폼 페이지로 이동
    @GetMapping("/join")
    public String showJoinForm() {
        return "joinForm"; // 회원가입 폼 뷰 페이지 (joinForm.html)
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String joinMember(@ModelAttribute UsersVO user, Model model) {
        // 중복 체크
        if (usersDAO.checkUserID(user.getUserID())) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");
            return "joinForm";
        }
        if (usersDAO.checkNName(user.getNName())) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "joinForm";
        }

        // 회원가입 처리
        boolean isJoined = usersDAO.joinMember(user);
        if (isJoined) {
            model.addAttribute("message", "회원가입이 완료되었습니다.");
            return "loginForm"; // 회원가입 성공 시 로그인 페이지로 이동 (loginForm.html)
        } else {
            model.addAttribute("error", "회원가입에 실패했습니다. 다시 시도해주세요.");
            return "joinForm";
        }
    }

    // 아이디 찾기 폼 페이지로 이동
    @GetMapping("/findID")
    public String showFindIDForm() {
        return "findIDForm"; // 아이디 찾기 폼 뷰 페이지 (findIDForm.html)
    }

    // 아이디 찾기 처리
    @PostMapping("/findID")
    public String findID(@RequestParam("phone") String phone, Model model) throws SQLException {
        String userID = findIDDAO.findID(phone);
        if (userID != null) {
            model.addAttribute("userID", userID);
            return "showID"; // 찾은 아이디를 보여주는 페이지 (showID.html)
        } else {
            model.addAttribute("error", "해당 전화번호로 가입된 아이디가 없습니다.");
            return "findIDForm";
        }
    }

    // 비밀번호 찾기 폼 페이지로 이동
    @GetMapping("/findPW")
    public String showFindPWForm() {
        return "findPWForm"; // 비밀번호 찾기 폼 뷰 페이지 (findPWForm.html)
    }

    // 비밀번호 찾기 처리
    @PostMapping("/findPW")
    public String findPW(@RequestParam("userID") String userID, @RequestParam("pwKey") String pwKey, Model model) throws SQLException {
        String userPW = findPWDAO.findPW(userID, pwKey);
        if (userPW != null) {
            model.addAttribute("userPW", userPW);
            return "showPW"; // 찾은 비밀번호를 보여주는 페이지 (showPW.html)
        } else {
            model.addAttribute("error", "제시어가 일치하지 않거나 잘못된 아이디입니다.");
            return "findPWForm";
        }
    }
}
