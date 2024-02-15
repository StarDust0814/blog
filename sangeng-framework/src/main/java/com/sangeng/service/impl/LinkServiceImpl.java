package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddLinkDto;
import com.sangeng.domain.dto.UpdateLinkDto;
import com.sangeng.domain.entity.Link;
import com.sangeng.domain.vo.LinkInfoVo;
import com.sangeng.domain.vo.LinkVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.LinkService;
import com.sangeng.mapper.LinkMapper;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author RS.Meta
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2024-01-23 18:46:52
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        // 查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> links = list(queryWrapper);
        // 转换成VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult listAllLink(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(name),Link::getName,name);
        queryWrapper.eq(StringUtils.hasText(status),Link::getStatus,status);
        Page<Link> page = new Page<>();
        page(page, queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(AddLinkDto addLinkDto) {
        Link link = BeanCopyUtils.copyBean(addLinkDto, Link.class);

        return save(link) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getLinkInfo(Long id) {
        Link link = getById(id);
        String name = link.getName();
        String logo = link.getLogo();
        String description = link.getDescription();
        String address = link.getAddress();
        String status = link.getStatus();

        LinkInfoVo linkInfoVo = new LinkInfoVo();
        linkInfoVo.setAddress(address);
        linkInfoVo.setDescription(description);
        linkInfoVo.setId(id.toString());
        linkInfoVo.setLogo(logo);
        linkInfoVo.setName(name);
        linkInfoVo.setStatus(status);



        return ResponseResult.okResult(linkInfoVo);
    }

    @Override
    public ResponseResult updateLink(UpdateLinkDto updateLinkDto) {
        String name = updateLinkDto.getName();
        String description = updateLinkDto.getDescription();
        String address = updateLinkDto.getAddress();
        String logo = updateLinkDto.getLogo();
        String status = updateLinkDto.getStatus();
        String id = updateLinkDto.getId();

        Link link = new Link();
        link.setId(Long.parseLong(id));
        link.setName(name);
        link.setLogo(logo);
        link.setDescription(description);
        link.setAddress(address);
        link.setStatus(status);


        return updateById(link) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        return removeById(id) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}




