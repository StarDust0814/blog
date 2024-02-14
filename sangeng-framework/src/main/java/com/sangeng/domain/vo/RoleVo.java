package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    private String id;
    private String remark;
    private String roleKey;
    private String roleName;
    private String roleSort;
    private String status;
}
