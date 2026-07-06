package com.example.hxce.api.util;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import com.example.hxce.api.dao.PermissionDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpUtilImpl implements StpInterface {

    @Resource
    private PermissionDao permissionDao;

    @Override
    public List<String> getPermissionList(Object o, String s) {
        long userId= Convert.toLong(o);
        ArrayList<String> list=permissionDao.searchUserPermissions(userId);
        return list;
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        return List.of();
    }
}
