package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



@Repository
public class PostListDAO {
    private final JdbcTemplate jdbcTemplate;

    public PostListDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PostsVO> selectPage(String value, int sel) {
        if (sel > sql.length) {
            System.out.println("PLDAO 입력값 코딩 오류");
            return null;
        }
        String sqlTemp = sql[sel];
        List<PostsVO> result = null;
        try {
            result = jdbcTemplate.query(sqlTemp,new Object[]{value}, new PostListRowMapper());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public List<PostsVO> selectPage() {
        String sqlTemp = "SELECT POSTNO, TITLE, NNAME, PDATE, CATE From VM_POSTS_PAGE";
        List<PostsVO> result = null;
        try {
            result = jdbcTemplate.query(sqlTemp, new PostListRowMapper());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    private final static String[] sql =
            {"SELECT * From VM_POSTS_PAGE WHERE NNAME LIKE ? ",
                    "SELECT * From VM_POSTS_PAGE WHERE TITLE LIKE ?",
                    "SELECT * From VM_POSTS_PAGE WHERE NNAME = ?",
                    "SELECT * From VM_POSTS_PAGE WHERE CATE = ?",
                    "SELECT * From VM_POSTS_PAGE WHERE POSTNO IN " +
                            "(SELECT POSTNO FROM LIKES WHERE NNAME = ?)",
                    "SELECT POSTNO, TITLE, CATE, PDATE, NNAME FROM VM_POSTS_PAGE " +
                            "WHERE POSTNO in (SELECT POSTNO FROM VM_COMM WHERE NNAME = ?)"
            };


    private static class PostListRowMapper implements RowMapper<PostsVO> {

        @Override
        public PostsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PostsVO(
                    rs.getInt("POSTNO"),
                    rs.getString("TITLE"),
                    null,
                    rs.getString("NNAME"),
                    rs.getDate("PDATE"),
                    rs.getString("CATE"),
                    null
            );
        }
    }
}




