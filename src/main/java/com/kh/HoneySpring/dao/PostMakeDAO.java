package com.kh.HoneySpring.dao;

import com.kh.HoneySpring.vo.PostsVO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PostMakeDAO {
    private final JdbcTemplate jdbcTemplate;

    public PostMakeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean PostmakeCreate(PostsVO postVO) {
        int rst = 0;
        String sql = "INSERT into Posts (POSTNO, TITLE, USERID, PCONTENT, PDATE, CATE) values(seq_postno.nextval, ?, ?, ?, sysdate, ?)";
        try {
            rst = jdbcTemplate.update(sql, postVO.getTitle(), postVO.getAuthor(), postVO.getContent(), postVO.getCategory());
        } catch (Exception e) {
            System.out.println(e + "잘못된 입력 입니다. 다시 시도해 주세요");
        }
        return rst > 0;
    }
}

