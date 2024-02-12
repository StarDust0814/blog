package com.sangeng.service;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sangeng.domain.vo.CategoryVo;

import java.util.List;

/**
* @author RS.Meta
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2024-01-23 11:04:44
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();
}
