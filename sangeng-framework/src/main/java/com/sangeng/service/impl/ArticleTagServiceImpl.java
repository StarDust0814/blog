package com.sangeng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.service.ArticleTagService;
import com.sangeng.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author RS.Meta
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2024-02-12 17:23:19
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




