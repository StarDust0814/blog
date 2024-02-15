package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLinkDto;
import com.sangeng.domain.dto.UpdateLinkDto;
import com.sangeng.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author RS.Meta
* @description 针对表【sg_link(友链)】的数据库操作Service
* @createDate 2024-01-23 18:46:53
*/
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult listAllLink(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addLink(AddLinkDto addLinkDto);

    ResponseResult getLinkInfo(Long id);

    ResponseResult updateLink(UpdateLinkDto updateLinkDto);

    ResponseResult deleteLink(Long id);
}
