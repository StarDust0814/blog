package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.ArticleTag;
import com.sangeng.domain.entity.Category;
import com.sangeng.domain.entity.Tag;
import com.sangeng.domain.vo.ArticleDetailVo;
import com.sangeng.domain.vo.ArticleListVo;
import com.sangeng.domain.vo.HotArticleVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.service.ArticleService;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.service.ArticleTagService;
import com.sangeng.service.CategoryService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author RS.Meta
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2024-01-22 14:53:13
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        // 查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,0);
        queryWrapper.orderByDesc(Article::getViewCount);
        // 只返回前10条
        Page<Article> page = new Page(1, 10);
        page(page, queryWrapper);
        List<Article> articles = page.getRecords();
        // 查询浏览量
        articles.stream()
                .map(article -> article.setViewCount(((Integer)redisCache.getCacheMapValue("article:viewCount", article.getId().toString())).longValue()))
                .collect(Collectors.toList());
        // bean拷贝封装成一个VO
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 如果传入了id，那就是查询具体的文章，如果没有传id，那就是查询所有的，mp的eq(boolean这个参数支持true的时候，当前eq生效)
        articleLambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,articleLambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        // 查询类别的名称
        // categoryName
        // 查询浏览量
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .map(article -> article.setViewCount(((Integer)redisCache.getCacheMapValue("article:viewCount", article.getId().toString())).longValue()))
                .collect(Collectors.toList());



        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        PageVo pagevo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pagevo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 根据ID查询
        Article article = getById(id);
        // 获取浏览量
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewCount.longValue());
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        // 根据分类ID查询对应名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult add(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);

        List<ArticleTag> articleTags = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(title),Article::getTitle,title);
        queryWrapper.eq(StringUtils.hasText(summary),Article::getSummary,summary);
        Page<Article> page = new Page<Article>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}




