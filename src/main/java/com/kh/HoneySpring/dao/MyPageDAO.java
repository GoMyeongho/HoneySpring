package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MyPageDAO {
    private final JdbcTemplate jdbcTemplate;

    public MyPageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UsersVO> usersSelect() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, new UsersRowMapper());
    }

    public void usersUpdate(UsersVO vo) { // 업데이트는 반환값이 필요 없으므로 void 사용
        String sql = "UPDATE USERS SET USERPW = ?, NNAME=?, PHONE=?, PWLOCK=?, PWKey=? WHERE USERID = ?";
        jdbcTemplate.update(sql,
                vo.getUserPW(),
                vo.getNName(),
                vo.getPhone(),
                vo.getPwLOCK(),
                vo.getPwKey());
    }

    private static class UsersRowMapper implements RowMapper<UsersVO> {
        @Override
        public UsersVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UsersVO(
                    rs.getString("USERID"),
                    rs.getString("USERPW"),
                    rs.getString("NNAME"),
                    rs.getString("PHONE"),
                    rs.getDate("UDATE"),
                    rs.getString("PWLOCK"),
                    rs.getString("PWKEY")
            );
        }
    }
    public void usersSelectResult(List<UsersVO> list) {
        System.out.println("----------------------------------------------");
        System.out.println("             회원 정보");
        System.out.println("----------------------------------------------");
        for (UsersVO e : list) {
            System.out.print(e.getUserID() + " ");
            System.out.print(e.getUserPW() + " ");
            System.out.print(e.getNName() + " ");
            System.out.print(e.getPhone() + " ");
            System.out.print(e.getUpdateDATE() + " ");
            System.out.print(e.getPwLOCK() + " ");
            System.out.print(e.getPwKey() + " ");
            System.out.println();
        }
        System.out.println("----------------------------------------------");
    }
}
