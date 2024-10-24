package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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

    public boolean usersUpdate(UsersVO vo) {
        
        
    }

    private static class UsersRowMapper implements RowMapper<UsersVO> {
        @Override
        public UsersVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UsersVO(
                    rs.getString("USER_ID"),
                    rs.getString("USER_PW"),
                    rs.getString("NNAME"),
                    rs.getString("PHONE"),
                    rs.getDate("UPDATE_DATE"),
                    rs.getString("PW_LOCK"),
                    rs.getString("PW_KEY")
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
