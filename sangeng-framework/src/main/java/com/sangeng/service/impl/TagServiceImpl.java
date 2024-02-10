package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.Tag;
import com.sangeng.service.TagService;
import com.sangeng.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author RS.Meta
* @description 针对表【sg_tag(标签)】的数据库操作Service实现
* @createDate 2024-02-10 20:40:42
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




