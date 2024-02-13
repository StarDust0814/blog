package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author RS.Meta
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2024-01-22 14:53:13
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto addArticleDto);

    ResponseResult listAllArticle(Integer pageNum, Integer pageSize, String title, String summary);
}
