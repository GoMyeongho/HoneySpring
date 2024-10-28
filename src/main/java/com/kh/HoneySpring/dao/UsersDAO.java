package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.Common.Common;
import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UsersDAO {
    static Connection conn = null;
    static PreparedStatement psmt = null;
    static ResultSet rs = null;

    // 회원가입 메서드----------------------------------------------------------------------
    public boolean joinMember(UsersVO user) {
        boolean isSuccess = false;
        String query = "INSERT INTO USERS (userID, userPW, nName, phone, UDATE, pwLOCK, pwKey) " +
                "VALUES (?, ?, ?, ?, SYSDATE, ?, ?)";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, user.getUserID());
            psmt.setString(2, user.getUserPW());
            psmt.setString(3, user.getNName());
            psmt.setString(4, user.getPhone());
            psmt.setString(5, user.getPwLOCK());
            psmt.setString(6, user.getPwKey());
            int result = psmt.executeUpdate();
            isSuccess = (result > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(psmt);
            Common.close(conn);
        }
        return isSuccess;
    }

    // 아이디 중복 체크
    public boolean checkUserID(String userID) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM USERS WHERE userID = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, userID);
            rs = psmt.executeQuery();
            if (rs.next()) {
                exists = (rs.getInt(1) > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return exists;
    }

    // 닉네임 중복 체크
    public boolean checkNName(String nName) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM USERS WHERE nName = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, nName);
            rs = psmt.executeQuery();
            if (rs.next()) {
                exists = (rs.getInt(1) > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return exists;
    }

    // 아이디 찾기 메서드-----------------------------------------------------------------
    public String findID(String phone) {
        String userID = null;
        String query = "SELECT userID FROM USERS WHERE phone = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, phone);
            rs = psmt.executeQuery();
            if (rs.next()) {
                userID = maskUserID(rs.getString("userID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return userID;
    }

    // 비밀번호 찾기 메서드----------------------------------------------------------
    public String findPW(String userID, String pwKey) {
        String userPW = null;
        String query = "SELECT userPW FROM USERS WHERE userID = ? AND pwKey = ?";
        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, userID);
            psmt.setString(2, pwKey);
            rs = psmt.executeQuery();
            if (rs.next()) {
                userPW = rs.getString("userPW");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        return userPW;
    }

    // 유저 아이디 마스킹----------------------------------------------------------------
    private String maskUserID(String userID) {
        String visiblePart = userID.substring(0, 4);
        String maskedPart = "*".repeat(userID.length() - 4);
        return visiblePart + maskedPart;
    }
}


