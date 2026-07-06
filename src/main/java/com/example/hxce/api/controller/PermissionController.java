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
        ArrayList<HashMap> list = permissionService.searchAllPermission();
        return R.ok(new HashMap<>() {{
            put("permissions", list);
        }});
    }
}
