package com.sangeng.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.AddCategoryDto;
import com.sangeng.domain.dto.UpdateCategoryDto;
import com.sangeng.domain.entity.Category;
import com.sangeng.domain.vo.CategoryVo;
import com.sangeng.domain.vo.ExcelCategoryVo;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.service.CategoryService;
import com.sangeng.utils.BeanCopyUtils;
import com.sangeng.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
         List<CategoryVo> list = categoryService.listAllCategory();
         return ResponseResult.okResult(list);
     }

     @GetMapping("/list")
     public ResponseResult listAllCategory(@RequestParam("pageNum") Integer pageNum,
                                           @RequestParam("pageSize") Integer pageSize,
                                           @RequestParam(value = "name",required = false) String name,
                                           @RequestParam(value="status",required = false) String status){
        return categoryService.listAllCategory(pageNum,pageSize,name,status);
     }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
     @GetMapping("/export")
    public void export(HttpServletResponse response){
        try{
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            List<Category> categoryVos = categoryService.list();
            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            EasyExcel.write(response.getOutputStream(),ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出").doWrite(excelCategoryVos);
        }catch (Exception e){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
     }

     @PostMapping
    public ResponseResult addCategory(@RequestBody AddCategoryDto addCategoryDto){
        return categoryService.addCategory(addCategoryDto);
     }

    @GetMapping("/{id}")
    public ResponseResult getCategoryInfo(@PathVariable Long id){
        return categoryService.getCategoryInfo(id);
    }

    @PutMapping
    public ResponseResult updateCategory(@RequestBody UpdateCategoryDto addCategoryDto){
        return categoryService.updateCategory(addCategoryDto);
     }

     @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
