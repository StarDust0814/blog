package com.sangeng.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sangeng.constants.SystemConstants;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCategoryDto;
import com.sangeng.domain.dto.UpdateCategoryDto;
import com.sangeng.domain.entity.Article;
import com.sangeng.domain.entity.Category;
import com.sangeng.domain.vo.CategoryInfoVo;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.ArticleService;
import com.sangeng.service.CategoryService;
import com.sangeng.mapper.CategoryMapper;
import com.sangeng.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author RS.Meta
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2024-01-23 11:04:44
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;


    @Override
    public ResponseResult getCategoryList() {
        // 只查询发布状态的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);

        List<Article> articleList = articleService.list(articleWrapper);
        // 获取文章分类并且去重
        Set<Long> categoryIds = articleList.stream().
                map(Article::getCategoryId).
                collect(Collectors.toSet());

        // 查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        // 封装
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public ResponseResult listAllCategory(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name),Category::getName,name);
        wrapper.eq(StringUtils.hasText(status),Category::getStatus,status);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        return save(category) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult getCategoryInfo(Long id) {
        Category category = getById(id);
        Long cid = category.getId();
        String name = category.getName();
        String description = category.getDescription();
        String status = category.getStatus();

        CategoryInfoVo categoryInfoVo = new CategoryInfoVo();
        categoryInfoVo.setDescription(description);
        categoryInfoVo.setName(name);
        categoryInfoVo.setId(cid.toString());
        categoryInfoVo.setStatus(status);


       return ResponseResult.okResult(categoryInfoVo);
    }

    @Override
    public ResponseResult updateCategory(UpdateCategoryDto addCategoryDto) {
        String description = addCategoryDto.getDescription();
        String name = addCategoryDto.getName();
        String id = addCategoryDto.getId();
        String status = addCategoryDto.getStatus();

        Category category = new Category();
        category.setId(Long.parseLong(id));
        category.setName(name);
        category.setDescription(description);
        category.setStatus(status);

        return updateById(category) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult deleteCategory(Long id) {
        return removeById(id) ? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }


}




