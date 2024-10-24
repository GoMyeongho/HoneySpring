package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class UsersDAO {
    static Connection conn = null;
    static Statement stmt = null;
    PreparedStatement psmt = null;
    static ResultSet rs = null;
    static Scanner sc = null;

    public void joinMember() {
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
            phone = sc.next();
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
            sc.nextLine();
            System.out.println("비밀번호를 찾을 시 사용 할 질문을 입력 해 주세요");
            System.out.println("질문에는 비밀번호가 포함되어 있으면 안되며 한글 기준 20자 이내로 입력 해 주세요.");
            System.out.print("질문 입력: ");
            pwLOCK = sc.nextLine();
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
            int ret = psmt.executeUpdate();
            System.out.println("Return: " + ret);

        } catch (SQLException e) {
            System.out.println(e + "회원가입에 실패하였습니다.");
        }
        Common.close(psmt);
        Common.close(conn);
    }


    // !!!!!아이디 찾기------------------------------------------------------------------------------------------
    public void findID() throws SQLException {
        String userID;
        Scanner sc = new Scanner(System.in);
        String phone;
        List<String> phoneList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
            while(rs.next()){
                String Phone = rs.getString("Phone");
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
                rs = stmt.executeQuery("SELECT userID FROM USERS WHERE PHONE = '" + phone +"';");
                if (rs.next()) {
                    userID = rs.getString("USERID");
                    String maskedID = maskUserID(userID);
                    System.out.println("아이디는 " + maskedID + "입니다.");
                } else {
                    System.out.println("해당 전화번호로 가입된 아이디가 없습니다.");
                }
            }catch (Exception e) {
                System.out.println(e + "연결 실패");
                return;
            }
            break;
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
    }


    // !!!!!!비밀번호 찾기------------------------------------------------------------------------------
    public void findPW() throws SQLException {
        String userID, pwLOCK, pwKey, userPW;
        Scanner sc = new Scanner(System.in);
        List<String> IDList = new ArrayList<>();
        try {
            conn = Common.getConnection();  // 오라클 DB 연결
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT USERID FROM VM_LOGIN");
            while(rs.next()){
                String ID = rs.getString("USERID");
                IDList.add(ID);
            }
        } catch (Exception e){
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
            if (rs.next()) {
                userPW = rs.getString("USERPW");
            } else {
                System.out.println("해당 아이디로 가입된 계정이 없습니다.");
                return;
            }

            if (userID.getBytes().length >= 8 && userID.getBytes().length <= 16) {
            } else {
                System.out.println("아이디 입력 조건을 다시 확인 해 주세요");
                continue;
            }
            for (String e : IDList) {
                if (IDList.equals(e)){
                }
                else {
                }
            }
            break;
        }

        try {
            conn = Common.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT pwLOCK FROM USERS WHERE userID = '" + userID +"';");
            rs.next();
            pwLOCK = rs.getString("pwLOCK");
        } catch (Exception e) {
            System.out.println(e + "연결 실패");
            return;
        }
        while (true) {
            System.out.println("제시문: " + pwLOCK);
            // 데이터베이스에 있는 아이디 확인하여 pwLOCK 가져오기
            System.out.println("키워드를 입력 해 주세요. 키워드는 한글 기준 8자 이하 입니다.");
            System.out.print("키워드: ");
            pwKey = sc.next();
            if (pwKey.getBytes().length <= 24) {

            } else {
                System.out.println("키워드 입력 조건을 다시 확인 해 주세요");
                continue;
            }
            try {
                conn = Common.getConnection();
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT userPW FROM USERS WHERE pwKey = '" + pwKey +"';");
                rs.next();
                userPW = rs.getString("userPW");
            }catch (Exception e) {
                System.out.println(e + "연결 실패");
                return;
            }
            System.out.print("비밀번호는 " + userPW + "입니다.");
            break;
        }
        Common.close(rs);
        Common.close(stmt);
        Common.close(conn);
    }

    //---------------------------------------------------------------------------------------------------------------------
    // 한글이 들어가지 않는지 확인하는 메서드(존재할 시 null리턴)
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

}
