package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.hxce.api.controller.form.SearchUserRolesForm;
import com.example.hxce.api.service.RoleService;
import com.example.hxce.api.util.R;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/searchAllRole")
    @SaCheckLogin
    public R searchAllRole(){
        ArrayList<HashMap> list=roleService.searchAllRole();
        return R.ok(new HashMap<>(){{
            put("roleList", list);
        }});
    }

    @PostMapping("/searchUserRoles")
    @SaCheckLogin
    public R searchUserRoles(@RequestBody @Validated SearchUserRolesForm form){
        ArrayList<String> roles=roleService.searchUserRoles(form.getUserId());
        return R.ok(new HashMap<>(){{
            put("roleList",roles);
        }});
    }
}
