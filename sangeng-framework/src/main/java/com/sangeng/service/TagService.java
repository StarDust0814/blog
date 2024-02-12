package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.vo.PageVo;

/**
* @author RS.Meta
* @description 针对表【sg_tag(标签)】的数据库操作Service
* @createDate 2024-02-10 20:40:42
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(TagListDto tagListDto);

    ResponseResult deleteTag(Long id);
}
