package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.hxce.api.service.PermissionService;
import com.example.hxce.api.util.R;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @GetMapping("/searchSystemicPermissions")
    @SaCheckLogin
    public R searchSystemicPermissions() {
        System.out.println("======== searchSystemicPermissions 被调用 ========");
        ArrayList<HashMap> list = permissionService.searchAllPermission();
        System.out.println("查询到的权限数据数量：" + (list != null ? list.size() : 0));
        if (list != null && list.size() > 0) {
            for (HashMap item : list) {
                System.out.println("权限模块：" + item);
            }
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("permissions", list);
        System.out.println("返回的完整结果：" + result);
        return R.ok(result);
    }
}
