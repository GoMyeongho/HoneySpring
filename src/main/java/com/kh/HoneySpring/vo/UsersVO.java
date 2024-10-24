package com.kh.HoneySpring.vo;
import lombok.*;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersVO {
    private String userID;
    private String userPW;
    private String nName;
    private String phone;
    private Date updateDATE;
    private String pwLOCK;
    private String pwKey;
}
