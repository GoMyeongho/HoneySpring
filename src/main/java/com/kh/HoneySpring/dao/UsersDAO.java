package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
public class UsersDAO {
    String userPW;
    private final JdbcTemplate jdbcTemplate;

    public UsersDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean joinMember(UsersVO vo) {
        int result = 0;
        String sql = "INSERT INTO users (userID, password, nickname, phone, hintStatement, hintWord) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            result = jdbcTemplate.update(sql, vo.getUserID(), vo.getUserPW(), vo.getNName(),
                    vo.getPhone() , vo.getPwLOCK(), vo.getPwKey());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }


    public boolean validateUserID(String userID) {
        if (userID.getBytes().length >= 8 && userID.getBytes().length <= 16) {
            return true;
        } else {
            System.out.println("-아이디 생성 조건을 다시 확인 후 입력 해 주세요.");
            return false;
        }
    }

    public boolean validatePW(String userPW) {
        if (userPW.getBytes().length >= 8 && userPW.getBytes().length <= 16) {
            return true;
        } else {
            System.out.println("-비밀번호 생성 조건을 다시 확인 후 입력 해 주세요.");
        }
        return false;
    }

    public boolean validateConfirmPW(String userPW, String confirmPW) {
        return Objects.equals(userPW, confirmPW);
    }

    public boolean validateNickname(String nName) {
        if (nName.getBytes().length <= 24) {  // 바이트 기준
            return true;
        } else {
            System.out.println("-닉네임 생성 조건을 다시 확인 후 입력 해 주세요.");
            return false;
        }
    }

    public boolean validatePhone(String phone) {
        if (phone.getBytes().length == 13) {
            return true;
        } else {
            System.out.print("-전화번호를 확인 후 다시 입력 해 주세요");
            return false;
        }
    }

    public boolean validatePwLOCK(String pwLock) {
        if (pwLock.getBytes().length >= 60) {
            System.out.print("-제시문 생성 조건을 확인 후 다시 입력 해 주세요.");
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePwKey(String pwKey) {
        if (pwKey.getBytes().length <= 24) {
            return true;
        } else {
            System.out.print("-제시어 생성 조건을 확인 후 다시 입력 해 주세요.");
            return false;
        }
    }
}
