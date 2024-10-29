package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.Common.Common;
import org.springframework.stereotype.Repository;

import java.sql.*;
@Repository
public class findPWDAO {
    public String findPW(String userID, String pwKey) {
        String userPW = null;

        try (Connection conn = Common.getConnection();
             PreparedStatement psmt = conn.prepareStatement("SELECT pwLOCK, PWKEY, userPW FROM USERS WHERE userID = ?")) {

            psmt.setString(1, userID);
            ResultSet rs = psmt.executeQuery();

            if (rs.next()) {
                String actualPwKey = rs.getString("PWKEY");
                userPW = rs.getString("userPW");

                // 제시어 검증
                if (!actualPwKey.equals(pwKey)) {
                    return "제시어가 다릅니다.";
                }
            } else {
                return "해당 아이디로 가입된 계정이 없습니다.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "비밀번호 찾기 실패";
        }

        return userPW;
    }

    public String getPWLock(String userID) {
        String pwLOCK = null;
        try (Connection conn = Common.getConnection();
             PreparedStatement psmt = conn.prepareStatement("SELECT pwLOCK FROM USERS WHERE userID = ?")) {

            psmt.setString(1, userID);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                pwLOCK = rs.getString("pwLOCK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pwLOCK;
    }
}