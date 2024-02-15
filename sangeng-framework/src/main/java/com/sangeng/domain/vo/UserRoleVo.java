package com.sangeng.domain.vo;

import com.sangeng.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserRoleVo {
    private List<String> roleIds;
    private List<Role> roles;
    private UserRoleInfoVo user;
}
