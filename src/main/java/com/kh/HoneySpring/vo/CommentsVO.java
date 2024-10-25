package com.kh.HoneySpring.vo;

import lombok.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CommentsVO {
    private int postNo;
    private String nName;
    private String content;
    private Date cDate;
    private int commNo;
    private int subNo;
    private String userId;
}
