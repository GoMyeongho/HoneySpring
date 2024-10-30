package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.MyPageDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class MyPageController {
    private final MyPageDAO myPageDAO;

    public MyPageController(MyPageDAO myPageDAO) {
        this.myPageDAO = myPageDAO;
    }

    // 비밀번호 입력 페이지 이동 (GET 요청)
    @GetMapping("/mypagePassword")
    public String mypagePasswordPage() {
        return "thymeleaf/mypagePassword"; // 비밀번호 입력 페이지
    }

    // 비밀번호 검증 후 마이페이지로 이동 (POST 요청)
    @PostMapping("/verifyMypagePassword")
    public String verifyMypagePassword(
            @SessionAttribute(value = "login", required = false) UsersVO vo,
            @RequestParam("password") String password,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        // 세션에 사용자 정보가 없을 경우
        if (vo == null) {
            redirectAttributes.addFlashAttribute("error", "세션이 만료되었습니다. 다시 로그인해주세요.");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        // 비밀번호 확인 로직
        try {
            if (myPageDAO.checkPassword(vo.getUserID(), password)) { // 비밀번호 검증 메서드
                session.setAttribute("mypageAccess", true); // 마이페이지 접근 권한 설정
                return "redirect:/users/mypage"; // 마이페이지로 리다이렉트
            } else {
                redirectAttributes.addFlashAttribute("error", "비밀번호가 올바르지 않습니다.");
                return "redirect:/users/mypagePassword"; // 비밀번호가 틀리면 다시 입력 페이지로 리다이렉트
            }
        } catch (Exception e) {
            // 예외 처리 로직
            redirectAttributes.addFlashAttribute("error", "비밀번호 검증 중 오류가 발생했습니다.");
            return "redirect:/users/mypagePassword"; // 다시 비밀번호 입력 페이지로 리다이렉트
        }
    }

    // 사용자 목록 조회 페이지
    @GetMapping("/select")
    public String selectViewUser(@SessionAttribute("login") UsersVO vo, Model model) {
        UsersVO user = myPageDAO.findUserById(vo.getUserID());
        model.addAttribute("user", user);
        return "thymeleaf/selectInfo";
    }

    // 사용자 업데이트 폼 (GET 요청)
    @GetMapping("/update")
    public String updateUserForm(@SessionAttribute("login") UsersVO vo, Model model) {
        UsersVO userToUpdate = myPageDAO.findUserById(vo.getUserID());
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
        return "redirect:/users/select"; // 업데이트 완료 후 사용자 목록으로 리다이렉트
    }

    // 마이페이지 접근 (비밀번호 검증 후에만 접근 가능)
    @GetMapping("/mypage")
    public String mypage(@SessionAttribute("login") UsersVO vo, HttpSession session, Model model) {
        Boolean hasAccess = (Boolean) session.getAttribute("mypageAccess");

        // 마이페이지 접근 권한 확인
        if (hasAccess == null || !hasAccess) {
            return "redirect:/users/mypagePassword"; // 접근 권한이 없으면 비밀번호 입력 페이지로 리다이렉트
        }

        UsersVO user = myPageDAO.findUserById(vo.getUserID());
        model.addAttribute("user", user);
        return "thymeleaf/mypage"; // 마이페이지 템플릿
    }
}