package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UsersDAO {
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Scanner sc = new Scanner(System.in);

    // 회원가입
    public void JoinUser() {
        String userPW="", userID ="", nName, phone ="", pwLOCK, pwKey;
        // 아이디 생성
        while (true) {
            System.out.println("아이디는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다. (중복 불가능)");
            System.out.print("아이디: ");
            List<String> IDList = new ArrayList<>();
            try {
                userID = noKor();
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
                while(rs.next()){
                    String ID = rs.getString("USERID");
                    IDList.add(ID);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
                continue;
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (userID.getBytes().length >= 8 && userID.getBytes().length <= 16) {

            } else {
                System.out.println("아이디 생성 조건을 다시 확인 후 입력 해 주세요.");
                continue;
            }
            for (String e : IDList) {
                // if 중복된 아이디 확인
                if (userID.equals(e)){
                    System.out.println("이미 존재하는 아이디 입니다.");
                    continue;
                }
            }
            break;
        }
        // 비밀번호 생성
        while (true) {
            System.out.println("비밀번호는 8자 이상 16자 이하의 영문 및 숫자이어야 합니다.");
            System.out.println("비밀번호는 특수문자 및 영문 대소문자가 모두 포함되어야 합니다.");
            System.out.print("비밀번호: ");
            userPW = noKor();
            if (userPW == null) {
                System.out.println("비밀번호 생성 조건을 다시 확인 후 입력 해 주세요.");
                continue;
            }
            if (userPW.getBytes().length >= 8 && userPW.getBytes().length <= 16) {
                break;
            } else {
                System.out.println("비밀번호 생성 조건을 다시 확인 후 입력 해 주세요.");
            }
            if (!userPW.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#\\$%\\^&\\*]).{8,16}$")) {
                System.out.println("비밀번호는 8자 이상 16자 이하의 영문 대소문자, 숫자, 특수문자를 포함해야 합니다.");
                continue;
                // gpt가 추천해준 비밀번호 생성 로직
            }

        }
        // 닉네임 생성
        while (true) {
            System.out.println("(사이트)에서 사용하실 닉네임을 입력 해 주세요");
            System.out.println("닉네임은 한글 기준 8자 까지 그리고 영어, 숫자 기준 16자까지 가능하며 중복 불가능합니다.");
            System.out.print("닉네임: ");
            nName = sc.next();
            List<String> nNameList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT NNAME FROM USERS");
                while(rs.next()){
                    String NName = rs.getString("NNAME");
                    nNameList.add(NName);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (nName.getBytes().length <= 24) {  // 바이트 기준
            } else {
                System.out.println("닉네임 생성 조건을 다시 확인 후 입력 해 주세요.");
                continue;
            }
            for (String e : nNameList) {
                // if 중복된 닉네임
                if (nNameList.contains(nName)){
                    System.out.println("이미 존재하는 닉네임 입니다.");
                    continue;
                }
            }
            break;
        }
        // 전화번호 입력
        while (true) {
            System.out.println("전화번호를 입력 해 주세요(010-0000-0000, 형태로 하이픈을 포함하여 입력 해 주세요)");
            System.out.print("전화번호: ");
            phone = inputPhone();
            List<String> phonList = new ArrayList<>();
            try {
                conn = Common.getConnection();  // 오라클 DB 연결
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
                while(rs.next()){
                    String Phone = rs.getString("Phone");
                    phonList.add(Phone);
                }
            } catch (Exception e){
                System.out.println(e + " 연결 실패");
            } finally {
                Common.close(rs);
                Common.close(stmt);
                Common.close(conn);
            }
            if (phone.getBytes().length == 13) {
            } else {
                System.out.print("전화번호를 확인 후 다시 입력 해 주세요");
                continue;
            }
            for (String e : phonList) {
                // if 중복된 전화번호 확인
                if (phonList.equals(e)){
                    System.out.println("이미 가입된 번호 입니다.");
                    continue;
                }
            }
            break;
        }
        // 비밀번호 찾기 시 사용 할 질문 및 키워드
        while (true) {
            System.out.println("비밀번호를 찾을 시 사용 할 질문을 입력 해 주세요");
            System.out.println("질문에는 비밀번호가 포함되어 있으면 안되며 한글 기준 20자 이내로 입력 해 주세요.");
            System.out.print("질문 입력: ");
            pwLOCK = sc.next();
            if (pwLOCK.getBytes().length >= 60) {
                continue;
            } else {
                System.out.print("질문 생성 조건을 확인 후 다시 입력 해 주세요.");
            }
            if(pwLOCK.contains(userPW)) {
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("질문에 대한 키워드를 입력 해 주세요");
            System.out.println("키워드는 한글 기준 8자 이내로 입력 해 주세요");
            System.out.print("키워드: ");
            pwKey = sc.next();
            if (pwKey.getBytes().length <= 24) {
            } else {
                System.out.print("키워드 생성 조건을 확인 후 다시 입력 해 주세요.");
                continue;
            }
            if(pwKey.contains(userPW)) {
                continue;
            }
            break;
        }
        String query = "INSERT INTO USERS (userID, userPW, nName, phone, UDATE, pwLOCK, pwKey) " +
                "VALUES (?, ?, ?, ?, SYSDATE, ?, ?)";

        try {
            conn = Common.getConnection();
            psmt = conn.prepareStatement(query);
            psmt.setString(1, userID);
            psmt.setString(2, userPW);
            psmt.setString(3, nName);
            psmt.setString(4, phone);
            psmt.setString(5, pwLOCK);
            psmt.setString(6, pwKey);
            int ret = stmt.executeUpdate(query);
            System.out.println("Return: " + ret);

        } catch (SQLException e) {
            System.out.println(e + "회원가입에 실패하였습니다.");
        }
        Common.close(psmt);
        Common.close(conn);
    }





// 메서드------------------------------------------------------------------------
// 한글이 들어가지 않는지 확인하는 메서드(존재할 시 "" 리턴)
public String noKor() {
    String name = sc.next();
    for (int i = 0; i < name.length(); i++) {
        if (name.charAt(i) < 33 || name.charAt(i) > 126) return "";
    }
    return name;
}

    // 숫자와 - 외의 문자가 들어가지 않는지 확인하는 메서드(존재할 시 null리턴)
    public String inputPhone() {
        String phone = sc.next();
        for (int i = 0; i < phone.length(); i++) {
            if (phone.charAt(i) >= 48 || phone.charAt(i) <= 57 || phone.charAt(i) == 45)
                //phone.matches("^010-\\d{4}-\\d{4}$")  << gpt가 추천해준 정규식
                return phone;
        }
        System.out.println("전화번호 형식이 맞지 않습니다.");
        return null;
    }

    // 유저 아이디를 마스킹하는 메서드 (아이디 찾기에서 사용)
    public String maskUserID(String userID) {
        String visiblePart = userID.substring(0, 4);
        String maskedPart = "*".repeat(userID.length() - 4);
        // 앞 4자를 제외한 문구는 *로 표기
        return visiblePart + maskedPart;
    }

    public List<UsersVO> selectUsersInfo() {
        return List.of();
    }

}
