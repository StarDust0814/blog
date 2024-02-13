package com.sangeng.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuListVo {
    private Long id;


    private String menuName;


    private Long parentId;


    private Integer orderNum;

    private String path;


    private String component;

    private Integer isFrame;

    private String menuType;


    private String visible;


    private String status;


    private String perms;


    private String icon;

    private String remark;
}
