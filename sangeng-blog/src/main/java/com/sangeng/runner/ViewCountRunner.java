package com.sangeng.runner;

import com.sangeng.domain.entity.Article;
import com.sangeng.mapper.ArticleMapper;
import com.sangeng.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 应用启动时执行，将浏览量数据存储到redis中
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public void run(String... args) throws Exception{
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
















}
