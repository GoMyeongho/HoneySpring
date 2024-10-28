package com.kh.HoneySpring.controller;

import com.kh.HoneySpring.dao.UsersDAO;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/login")
public class MainController {
    private final UsersDAO usersDAO;
    private com.kh.HoneySpring.dao.findIDDAO findIDDAO;
    private com.kh.HoneySpring.dao.findPWDAO findPWDAO;

    public MainController (UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }
    @GetMapping("/joinUser")
    public String joinUser(Model model){
        model.addAttribute("joinuser", new UsersVO());
        return "Thymeleaf.joinUser";
    }
    @PostMapping("/joinUser")
    public String validAndJoin(@ModelAttribute("joinUser") UsersVO vo, Model model){
        List<Boolean> validate = new ArrayList<>();
        List<String> validString = List.of("아이디가 잘 못 됐습니다", "비밀번호가 잘 못 됐습니다.", "비밀번호가 일치하지 않습니다.",
                "닉네임이 잘 못 됐습니다.", "전화번호가 잘 못 됐습니다.", "제시문이 잘 못 됐습니다.", "제시어가 잘 못 됐습니다.");
        validate.add(usersDAO.validateUserID(vo.getUserID()));
        validate.add(usersDAO.validatePW(vo.getUserPW()));
//        validate.add(usersDAO.validateConfirmPassword());
        validate.add(usersDAO.validateNickname(vo.getNName()));
        validate.add(usersDAO.validatePhone(vo.getPhone()));
        validate.add(usersDAO.validatePwLOCK(vo.getPwLOCK()));
        validate.add(usersDAO.validatePwKey(vo.getPwKey()));
        boolean isValid = true;
        List<String> valid = new ArrayList<>();
        for (int i = 0; i < validate.size(); i++) {
            if(!validate.get(i)) {
                valid.add(validString.get(i));
                isValid = false;
            }
        }
        boolean isJoin = false;
        if(isValid) {
            isJoin = usersDAO.joinMember(vo);
        }
        model.addAttribute("valid",valid);
        model.addAttribute("isValid",isValid);
        model.addAttribute("isJoin",isJoin);
        return "Thymeleaf/submitJoin";
    }

//    @GetMapping("/validateUserID")
//    public String validateUserID(@RequestParam("userID") String userID, Model model) {
//        boolean isValid = usersDAO.validateUserID(userID);
//        model.addAttribute("userIDValid", isValid ? "사용 가능한 아이디입니다." : "이미 존재하는 아이디입니다.");
//        return "validationResult";  // 결과 메시지를 표시할 템플릿
//    }
//
//    @GetMapping("/validatePassword")
//    public String validatePassword(@RequestParam("password") String password, Model model) {
//        boolean isValid = usersDAO.validatePW(password);
//        model.addAttribute("passwordValid", isValid ? "사용 가능한 비밀번호입니다." : "비밀번호 조건이 맞지 않습니다.");
//        return "validationResult";
//    }
//
//    @GetMapping("/validateNickname")
//    public String validateNickname(@RequestParam("nickname") String nickname, Model model) {
//        boolean isValid = usersDAO.validateNickname(nickname);
//        model.addAttribute("nicknameValid", isValid ? "사용 가능한 닉네임입니다." : "이미 존재하는 닉네임입니다.");
//        return "validationResult";
//    }
//
//    @GetMapping("/validatePhone")
//    public String validatePhone(@RequestParam("phone") String phone, Model model) {
//        boolean isValid = usersDAO.validatePhone(phone);
//        model.addAttribute("phoneValid", isValid ? "사용 가능한 전화번호입니다." : "이미 가입된 전화번호입니다.");
//        return "validationResult";
//    }


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
            return "findID";
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
            return "login";
        } else {
            model.addAttribute("error", "제시어가 일치하지 않거나 잘못된 아이디입니다.");
            return "findPW";
        }
    }
}
