package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.Common.Common;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class findIDDAO {
    public String findID(String phone) {
        String userID = "";

        try (Connection conn = Common.getConnection();
             PreparedStatement psmt = conn.prepareStatement("SELECT userID FROM USERS WHERE PHONE = ?")) {

            // 전화번호 형식 확인
            if (!phone.matches("^010-\\d{4}-\\d{4}$")) {
                return null;
            }

            psmt.setString(1, phone);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                userID = rs.getString("userID");
                userID = maskUserID(userID);
                System.out.println("아이디는 " + userID + "입니다.");
            } else {
                System.out.println("해당 전화번호로 가입된 아이디가 없습니다.");
            }
        } catch (SQLException e) {
            System.out.println(e + " 연결 실패");
        }
        return userID;
    }

    private String maskUserID(String userID) {
        if (userID == null || userID.length() == 0) {
            throw new IllegalArgumentException("사용자 ID는 비어있을 수 없습니다.");
        }
        if (userID.length() < 4) {
            return userID; // 길이가 4 미만일 경우 원본 문자열 반환
        }
        String visiblePart = userID.substring(0, 4);
        String maskedPart = "*".repeat(userID.length() - 4);
        return visiblePart + maskedPart;
    }
}