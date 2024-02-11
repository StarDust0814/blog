package com.sangeng.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MenuVo {
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



    private Date createTime;



    private String remark;
    private List<MenuVo> children;

}
