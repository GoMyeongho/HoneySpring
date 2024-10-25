package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.Common.Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class findPWDAO {
    static Connection conn = null;
    static Statement stmt = null;
    PreparedStatement psmt = null;
    static ResultSet rs = null;
    static Scanner sc = null;

    public void findPW() throws SQLException {
        String userID, pwLOCK, pwKey, userPW;
        Scanner sc = new Scanner(System.in);
        List<String> IDList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
            while (rs.next()) {
                String ID = rs.getString("USERID");
                IDList.add(ID);
            }
        } catch (Exception e) {
            System.out.println(e + " 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        while (true) {
            System.out.println("가입한 아이디를 입력 해 주세요");
            System.out.print("아이디: ");
            System.out.println();
            userID = noKor();

            if (userID.getBytes().length >= 8 && userID.getBytes().length <= 16) {
            } else {
                System.out.println("아이디 입력 조건을 다시 확인 해 주세요");
                return;
            }
            if (IDList.contains(userID)) ;
            else {
                System.out.println("해당 아이디로 가입된 계정이 없습니다.");
                continue;
            }
            break;
        }

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT pwLOCK , PWKEY  FROM USERS WHERE userID = '" + userID + "'");
            rs.next();
            pwLOCK = rs.getString("PWLOCK");
            pwKey = rs.getString("PWKEY");

        } catch (Exception e) {
            System.out.println(e + "연결 실패");
            return;
        } finally {
            Common.close(rs);
            Common.close(psmt);
            Common.close(conn);
        }
        while (true) {
            System.out.println("제시문: " + pwLOCK);
            // 데이터베이스에 있는 아이디 확인하여 pwLOCK 가져오기
            System.out.println("제시어를 입력 해 주세요. 제시어는 한글 기준 8자 이하 입니다.");
            System.out.print("키워드: ");
            if (pwKey.equals(sc.nextLine())) {
            } else {
                System.out.println("제시어가 다릅니다");
                continue;
            }
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT userPW FROM USERS WHERE USERID = '" + userID + "'");
                rs.next();
                userPW = rs.getString("userPW");
            } catch (Exception e) {
                System.out.println(e + "연결 실패");
                return;
            } finally {
                Common.close(rs);
                Common.close(psmt);
                Common.close(conn);
            }
            System.out.println("비밀번호는 " + userPW + "입니다.");
            break;
        }
    }
    public String noKor() {
        String name = sc.next();
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) < 33 || name.charAt(i) > 126) return "";
        }
        return name;
    }
}
