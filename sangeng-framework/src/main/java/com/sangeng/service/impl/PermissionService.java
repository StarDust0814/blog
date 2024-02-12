package com.sangeng.service.impl;

import com.sangeng.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    /**
     * 判断用户是否具有permission
     */
    public boolean hasPermission(String permission){
        if(SecurityUtils.isAdmin()){
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}



