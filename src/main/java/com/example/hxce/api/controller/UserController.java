package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.example.hxce.api.controller.form.*;
import com.example.hxce.api.service.UserService;
import com.example.hxce.api.util.PageUtils;
import com.example.hxce.api.util.R;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/sendSmsCode")
    public R sendSmsCode(@RequestBody @Validated SendSmsCodeForm form) {
        userService.sendSmsCode(form.getPhone());
        return R.ok();
    }

    @PostMapping("/login")
//    @SaCheckLogin
    public R login(@RequestBody @Validated LoginForm form) {
        Map<String,Object> map = userService.login(form.getPhone(),form.getSmsCode());
        Long userId = (Long) map.get("userId");
        //ArrayList<String> roles = (ArrayList<String>) map.get("roles");
        //ArrayList<String> permissions = (ArrayList<String>) map.get("permissions");
        //生成Token令牌
        StpUtil.login(userId);
        String token=StpUtil.getTokenValue();
        map.remove("userId");
        map.put("token",token);
        return R.ok(map);
    }

    @GetMapping("/searchUserInfoById")
    @SaCheckLogin
    public R searchUserInfoById(){
        long id = StpUtil.getLoginIdAsLong();
        HashMap map = userService.searchUserInfoById(id);
        return R.ok(map);
    }

    @GetMapping("/logout")
    @SaCheckLogin
    public R logout(){
        //从请求头Token中获取用户ID
        long userId = StpUtil.getLoginIdAsLong();
        //销毁Token令牌
        StpUtil.logout(userId);
        return R.ok();
    }

    @PostMapping("/searchByPage")
    @SaCheckPermission(value={"ROOT","USER:SELECT"},mode= SaMode.OR)
    public R searchUserByPage(@RequestBody @Validated SearchUserByPageForm form){
        int page=form.getPage();
        int size=form.getSize();
        int start=(page-1)*size;
        Map param= BeanUtil.beanToMap(form);
        param.put("start",start);
        PageUtils pageUtils=userService.searchByPage(param);
        return R.ok(new HashMap<>(){{
            put("page",pageUtils);
        }});
    }

    @PostMapping("/insert")
    @SaCheckPermission(value={"ROOT","USER:INSERT"},mode=SaMode.OR)
    public R insert(@RequestBody @Validated InsertUserForm form){
        Map<String,Object> param=BeanUtil.beanToMap(form);
        userService.insert(param);
        return R.ok();
    }

    @PostMapping("/existsByPhone")
    @SaCheckLogin
    public R existsByPhone(@RequestBody @Validated SearchUserExistsByPhoneForm form){
        Map param=BeanUtil.beanToMap(form);
        boolean bool=userService.existByPhone(param);
        return R.ok(new HashMap<>(){{
            put("isConflict",bool);
        }});
    }

    @PostMapping("/searchById")
    @SaCheckPermission(value={"ROOT","USER:SELECT"},mode=SaMode.OR)
    public R searchById(@RequestBody @Validated SearchUserByIdForm form){
        HashMap map = userService.searchById(form.getId());
        return R.ok(map);
    }

    @PostMapping("/update")
    @SaCheckPermission(value={"ROOT","USER:UPDATE"},mode=SaMode.OR)
    public R update(@RequestBody @Validated UpdateUserForm form){
        Map <String,Object> param=BeanUtil.beanToMap(form);
        userService.update(param);
        StpUtil.logout(form.getId());
        return R.ok();
    }

    @PostMapping("/deleteByIds")
    @SaCheckPermission(value={"ROOT","USER:DELETE"},mode=SaMode.OR)
    public R deleteByIds(@RequestBody @Validated DeleteUserByIdsForm form){
        userService.deleteByIds(form.getIds());
        return R.ok();
    }
}
