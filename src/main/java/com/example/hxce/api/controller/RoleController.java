package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.bean.BeanUtil;
import com.example.hxce.api.controller.form.*;
import com.example.hxce.api.service.RoleService;
import com.example.hxce.api.util.PageUtils;
import com.example.hxce.api.util.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/searchAllRole")
    @SaCheckLogin
    public R searchAllRole() {
        var list = roleService.searchAllRole();
        return R.ok(new HashMap<>() {
            {
                put("roleList", list);
            }
        });
    }

    @PostMapping("/searchUserRoles")
    @SaCheckLogin
    public R searchUserRoles(@RequestBody @Validated SearchUserRolesForm form) {
        var roles = roleService.searchUserRoles(form.getUserId());
        return R.ok(new HashMap<>() {
            {
                put("roleList", roles);
            }
        });
    }

    @PostMapping("/searchByPage")
    @SaCheckPermission(value = { "ROOT", "ROLE:SELECT" }, mode = SaMode.OR)
    public R searchRoleByPage(@RequestBody @Validated SearchRoleByPageForm form) {
        int page = form.getPage();
        int size = form.getSize();
        int start = (page - 1) * size;
        Map param = BeanUtil.beanToMap(form);
        param.put("start", start);
        PageUtils pageUtils = roleService.searchByPage(param);
        return R.ok(new HashMap<>() {
            {
                put("page", pageUtils);
            }
        });
    }

    @PostMapping("/insert")
    @SaCheckPermission(value = { "ROOT", "ROLE:INSERT" }, mode = SaMode.OR)
    public R insert(@RequestBody @Validated InsertRoleForm form) throws JsonProcessingException {
        System.out.println("插入角色的表单数据：" + form);
        Map<String, Object> param = BeanUtil.beanToMap(form);
        param.put("status", 1);
        String permissionsJson = objectMapper.writeValueAsString(form.getPermissions());
        System.out.println("权限JSON：" + permissionsJson);
        param.put("permissions", permissionsJson);
        System.out.println("传递给 service 的参数：" + param);
        roleService.insert(param);
        System.out.println("插入角色成功");
        return R.ok();
    }

    @PostMapping("/checkRoleNameConflict")
    @SaCheckLogin
    public R checkRoleNameConflict(@RequestBody @Validated CheckRoleNameConflictForm form) {
        Map param = BeanUtil.beanToMap(form);
        boolean bool = roleService.checkRoleNameConflict(param);
        return R.ok(new HashMap<>() {
            {
                put("isConflict", bool);
            }
        });
    }

    @PostMapping("/searchById")
    @SaCheckPermission(value = { "ROOT", "ROLE:SELECT" }, mode = SaMode.OR)
    public R searchById(@RequestBody @Validated SearchRoleByIdForm form) {
        HashMap map = roleService.searchById(form.getId());
        // 转换 status 字段：1->启用，2->停用
        Object statusObj = map.get("status");
        if (statusObj != null) {
            int status = Integer.parseInt(statusObj.toString());
            String statusStr;
            if (status == 1) {
                statusStr = "1";
            } else {
                statusStr = "2";
            }
            map.put("status", statusStr);
        }
        return R.ok(map);
    }

    @PostMapping("/searchRoleUsers")
    @SaCheckLogin
    public R searchRoleUsers(@RequestBody @Validated SearchRoleByIdForm form) {
        var list = roleService.searchRoleUsers(form.getId());
        // 转换 status 字段：1->正常，2->冻结，3->离职
        for (HashMap item : list) {
            Object statusObj = item.get("status");
            if (statusObj != null) {
                int status = Integer.parseInt(statusObj.toString());
                String statusStr;
                switch (status) {
                    case 1:
                        statusStr = "正常";
                        break;
                    case 2:
                        statusStr = "冻结";
                        break;
                    case 3:
                        statusStr = "离职";
                        break;
                    default:
                        statusStr = "未知";
                }
                item.put("status", statusStr);
            }
        }
        return R.ok(new HashMap<>() {
            {
                put("userList", list);
            }
        });
    }

    @PostMapping("/update")
    @SaCheckPermission(value = { "ROOT", "ROLE:UPDATE" }, mode = SaMode.OR)
    public R update(@RequestBody @Validated UpdateRoleForm form) throws JsonProcessingException {
        System.out.println("更新角色的表单数据：" + form);
        Map<String, Object> param = BeanUtil.beanToMap(form);
        param.put("id", form.getId().longValue());
        String permissionsJson = objectMapper.writeValueAsString(form.getPermissions());
        System.out.println("权限JSON：" + permissionsJson);
        param.put("permissions", permissionsJson);
        if (form.getStatus() != null) {
            param.put("status", form.getStatus().byteValue());
        }
        System.out.println("传递给 service 的参数：" + param);
        roleService.update(param);
        System.out.println("更新角色成功");
        return R.ok();
    }

    @PostMapping("/deleteByIds")
    @SaCheckPermission(value = { "ROOT", "ROLE:DELETE" }, mode = SaMode.OR)
    public R deleteByIds(@RequestBody @Validated DeleteRoleByIdsForm form) {
        Long[] ids = new Long[form.getIds().length];
        for (int i = 0; i < form.getIds().length; i++) {
            ids[i] = form.getIds()[i].longValue();
        }
        roleService.deleteByIds(ids);
        return R.ok();
    }
}
