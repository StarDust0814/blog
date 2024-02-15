package com.sangeng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private String userName;
    private String nickName;
    private String email;
    private String sex;
    private String status;
    private List<String> roleIds;
    private String id;
}
