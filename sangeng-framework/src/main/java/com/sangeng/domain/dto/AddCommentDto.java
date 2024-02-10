package com.sangeng.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sangeng.domain.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论DTO")
public class AddCommentDto {

    private Long id;


    private String type;

    @ApiModelProperty(notes = "文章id")
    private Long articleId;


    private Long rootId;


    private String content;


    private Long toCommentUserId;

    private Long toCommentId;


    private Long createBy;


    private Date createTime;


    private Long updateBy;


    private Date updateTime;

}
