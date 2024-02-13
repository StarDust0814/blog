package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoVo {
    private Long id;


    private String menuName;


    private Long parentId;


    private Integer orderNum;

    private String path;


    private String menuType;


    private String visible;


    private String status;


    private String icon;

    private String remark;
}
