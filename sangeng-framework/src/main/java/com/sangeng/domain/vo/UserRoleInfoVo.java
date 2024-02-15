package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRoleInfoVo {
    private Long id;
    private String nickName;
    private String sex;
    private String email;
    private String status;
    private String userName;
}
