package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.Common.Common;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Repository
public class findIDDAO {
    static Connection conn = null;
    static Statement stmt = null;
    PreparedStatement psmt = null;
    static ResultSet rs = null;
    static Scanner sc = null;

    public String findID(String phone) throws SQLException {
        String userID = "";
        Scanner sc = new Scanner(System.in);
        List<String> phoneList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT PHONE FROM USERS");
            while(rs.next()){
                String Phone = rs.getString("PHONE");
                phoneList.add(Phone);
            }
        } catch (Exception e){
            System.out.println(e + " 연결 실패");
        } finally {
            Common.close(rs);
            Common.close(stmt);
            Common.close(conn);
        }
        while (true) {
            System.out.println("가입 시 사용한 전화번호를 입력 해 주세요");
            System.out.println("전화번호는 010-0000-0000 형식으로 하이픈을 포함하여 입력 해 주세요");
            System.out.print("전화번호: ");
            phone = inputPhone();

            if (phone.getBytes().length == 13) {
            } else {
                System.out.print("전화번호 입력 조건을 확인 후 다시 입력 해 주세요.");
                continue;
            }
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT userID FROM USERS WHERE PHONE = '" + phone +"'");
                if (rs.next()) {
                    userID = rs.getString("USERID");
                    String maskedID = maskUserID(userID);
                    System.out.println("아이디는 " + maskedID + "입니다.");
                } else {
                    System.out.println("해당 전화번호로 가입된 아이디가 없습니다.");
                }
            }catch (Exception e) {
                System.out.println(e + "연결 실패");
                return userID;
            }
            break;
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
        return userID;
    }

    // 숫자와 - 외의 문자가 들어가지 않는지 확인하는 메서드(존재할 시 null리턴)
    public String inputPhone() {
        String phone = sc.next();
        for (int i = 0; i < phone.length(); i++) {
            if (phone.matches("^010-\\d{4}-\\d{4}$"))
                //  << gpt가 추천해준 정규식
                return phone;
        }
        System.out.println("전화번호 형식이 맞지 않습니다.");
        return null;
    }
    public String maskUserID(String userID) {
        String visiblePart = userID.substring(0, 4);
        String maskedPart = "*".repeat(userID.length() - 4);
        // 앞 4자를 제외한 문구는 *로 표기
        return visiblePart + maskedPart;
    }
}
