package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddArticleDto;
import com.sangeng.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto addArticleDto){
        return articleService.add(addArticleDto);
    }

    @GetMapping("/list")
    public ResponseResult listAllArticle(@RequestParam("pageNum") Integer pageNum,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam(value = "title",required = false) String title,
                                         @RequestParam(value = "summary",required = false) String summary){
        return articleService.listAllArticle(pageNum,pageSize,title,summary);
    }
}
