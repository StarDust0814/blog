package com.sangeng.controller;

import com.sangeng.domain.ResponseResult;
import com.sangeng.domain.dto.TagDto;
import com.sangeng.domain.dto.TagListDto;
import com.sangeng.domain.vo.PageVo;
import com.sangeng.domain.vo.TagVo;
import com.sangeng.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto ){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
         return tagService.addTag(tagListDto);
     }

     @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagInfo(@PathVariable Long id){
        return tagService.getTagInfo(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto) {
        return tagService.updateTag(tagDto);
    }

    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
         List<TagVo> list = tagService.listAllTag();
         return ResponseResult.okResult(list);
     }

}


