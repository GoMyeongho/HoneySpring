package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.UsersVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LoginDAO {
    private final JdbcTemplate jdbcTemplate;

    public LoginDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UsersVO FindByUserID(String userID) {
        String sql = "SELECT * FROM USERS WHERE USERID = ?";

        RowMapper<UsersVO> rowMapper = new RowMapper<UsersVO>() {
            @Override
            public UsersVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                UsersVO usersVO = new UsersVO();
                usersVO.setUserID(rs.getString("USERID"));
                usersVO.setUserPW(rs.getString("USERPW"));
                return usersVO;
            }
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, userID);
    }
}
