package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PostViewDAO {
    private final JdbcTemplate jdbcTemplate;

    public PostViewDAO(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public PostsVO viewPost(int postNo) {
        List<PostsVO> result = null;
        String sql = "select * from VM_POST where POSTNO = ?";
            try {
            result = jdbcTemplate.query(sql,new Object[]{postNo}, new PostViewRowMapper());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
            return result != null ? result.get(0) : null;
    }
    public boolean updatePost(PostsVO vo){
        String sql = "UPDATE POSTS SET TITLE = ?, PCONTENT = ?, CATE = ? WHERE POSTNO = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, vo.getTitle(), vo.getContent(), vo.getCategory(), vo.getPostno());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

    public boolean deletePost(int postNo) {
        String sql = "UPDATE POSTS SET CATE = 'DELETE' WHERE POSTNO = ?";
        int result = 0;
        try {
            result = jdbcTemplate.update(sql, postNo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result > 0;
    }

















    private static class PostViewRowMapper implements RowMapper<PostsVO> {

        @Override
        public PostsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PostsVO(
                    rs.getInt("POSTNO"),
                    rs.getString("TITLE"),
                    rs.getString("PCONTENT"),
                    rs.getString("NNAME"),
                    rs.getDate("PDATE"),
                    rs.getString("CATE"),
                    rs.getString("USERID")
            );
        }
    }
}
