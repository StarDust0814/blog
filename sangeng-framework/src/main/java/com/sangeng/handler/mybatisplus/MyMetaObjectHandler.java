package com.sangeng.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.sangeng.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 配置MP字段的自动填充
 * 而且它从LoginUser中获取userId，相当于从token中获取用户信息
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = null;
        try{
            userId = SecurityUtils.getUserId();

        }catch (Exception e){
            e.printStackTrace();
            userId = -1L;
        }
//        userId = SecurityUtils.getUserId();
        // 执行插入操作时，会给插入的数据行填充这些字段
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("createBy",userId,metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("updateBy",userId,metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName(" ",SecurityUtils.getUserId(),metaObject);
    }
}
