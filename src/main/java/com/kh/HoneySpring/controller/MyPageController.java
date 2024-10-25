package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.MyPageDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String selectViewEmp(Model model) {
        List<UsersVO> users = myPageDAO.usersSelect();
        model.addAttribute("users", users);
        return "thymeleaf/usersSelect";
    }

    // 사용자 정보를 조회하는 메소드 (중복 방지)
    private UsersVO getUserById(String userId) {
        return myPageDAO.usersSelect().stream()
                .filter(user -> user.getUserID().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + userId));
    }

    // 사용자 업데이트 폼 (GET 요청)
    @GetMapping("/update")
    public String updateUserForm(@RequestParam("userId") String userId, Model model) {
        UsersVO userToUpdate = getUserById(userId);
        model.addAttribute("user", userToUpdate);
        return "thymeleaf/updateUserForm";
    }

    // 사용자 업데이트 처리 (POST 요청)
    @PostMapping("/update")
    public String updateUser(@ModelAttribute UsersVO user, @RequestParam("userId") String userId, Model model) {
        user.setUserID(userId);  // 반드시 userId를 설정
        myPageDAO.usersUpdate(user);  // 업데이트 처리
        return "redirect:/users/select";
    }

    // 사용자 조회 결과 출력 페이지
    @GetMapping("/print")
    public String printUsers(Model model) {
        List<UsersVO> users = myPageDAO.usersSelect();
        myPageDAO.usersSelectResult(users);  // 콘솔에 사용자 정보를 출력
        model.addAttribute("users", users);
        return "thymeleaf/usersPrint";
    }
}
