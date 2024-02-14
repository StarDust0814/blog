package com.sangeng.domain.vo;

import com.sangeng.domain.entity.RoleMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeSelectVo {
    private List<MenuTreeSelectVo> menus;
    private List<String> checkedKeys;
}
