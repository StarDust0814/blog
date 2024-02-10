package com.sangeng.service.impl;

import com.sangeng.config.MinioProp;
import com.sangeng.domain.ResponseResult;
import com.sangeng.enums.AppHttpCodeEnum;
import com.sangeng.exception.SystemException;
import com.sangeng.service.UploadService;
import com.sangeng.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private MinioProp minioProp;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        String url = "";
        // 判断文件类型
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();
        if(!originalFilename.endsWith(".png")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        try{
            System.out.println(minioProp.getBucketName());
            url = minioUtil.uploadFile(img,minioProp.getBucketName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseResult.okResult(url);
    }
}
