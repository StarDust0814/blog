package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private String userName;
    private String nickName;
    private String password;
    private String phonenumber;
    private String email;
    private String sex;
    private String status;
    private List<String> roleIds;
}
