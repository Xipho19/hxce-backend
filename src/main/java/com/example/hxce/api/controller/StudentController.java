package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.example.hxce.api.controller.form.*;
import com.example.hxce.api.service.StudentService;
import com.example.hxce.api.util.PageUtils;
import com.example.hxce.api.util.R;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/sendSmsCode")
    public R sendSmsCode(@RequestBody @Validated SendSmsCodeForm form){
        studentService.sendSmsCode(form.getPhone());
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody @Validated LoginForm form){
        Map<String,Object> map = studentService.login(form.getPhone(),form.getSmsCode());
        Long studentId = (Long) map.get("studentId");
        StpUtil.login(studentId,"APP");
        String token=StpUtil.getTokenValue();
        map.put("token",token);
        return R.ok(map);
    }

    @PostMapping("/searchByPage")
    @SaCheckPermission(value={"ROOT","STUDENT:SELECT"},mode= SaMode.OR)
    public R searchStudentByPage(@RequestBody @Validated SearchStudentByPageForm form){
        int page=form.getPage();
        int size=form.getSize();
        int start=(page-1)*size;
        Map param= BeanUtil.beanToMap(form);
        param.put("start",start);
        PageUtils pageUtils=studentService.searchByPage(param);
        return R.ok(new HashMap<>(){{
            put("page",pageUtils);
        }});
    }

    @PostMapping("/insert")
    @SaCheckPermission(value={"ROOT","STUDENT:INSERT"},mode=SaMode.OR)
    public R insert(@RequestBody @Validated InsertStudentForm form){
        Map<String,Object> param=BeanUtil.beanToMap(form);
        studentService.insert(param);
        return R.ok();
    }

    @PostMapping("/existsByPhone")
    @SaCheckLogin
    public R existsByPhone(@RequestBody @Validated SearchUserExistsByPhoneForm form){
        Map param=BeanUtil.beanToMap(form);
        boolean bool=studentService.existByPhone(param);
        return R.ok(new HashMap<>(){{
            put("isConflict",bool);
        }});
    }

    @PostMapping("/searchById")
    @SaCheckPermission(value={"ROOT","STUDENT:SELECT"},mode=SaMode.OR)
    public R searchById(@RequestBody @Validated SearchStudentByIdForm form){
        HashMap map = studentService.searchById(form.getId());
        System.out.println("查询学生详情的原始数据：" + map);
        // 转换 status 字段，返回数字，方便前端编辑时转换
        Object statusObj = map.get("status");
        if (statusObj != null) {
            int status = Integer.parseInt(statusObj.toString());
            map.put("status", status);
        }
        System.out.println("查询学生详情的处理后数据：" + map);
        return R.ok(map);
    }

    @PostMapping("/update")
    @SaCheckPermission(value={"ROOT","STUDENT:UPDATE"},mode=SaMode.OR)
    public R update(@RequestBody @Validated UpdateStudentForm form){
        Map <String,Object> param=BeanUtil.beanToMap(form);
        System.out.println("更新学生的参数：" + param);
        // 处理 status 字段类型
        if (form.getStatus() != null) {
            param.put("status", form.getStatus().byteValue());
        }
        studentService.update(param);
        return R.ok();
    }

    @PostMapping("/deleteByIds")
    @SaCheckPermission(value={"ROOT","STUDENT:DELETE"},mode=SaMode.OR)
    public R deleteByIds(@RequestBody @Validated DeleteStudentByIdsForm form){
        studentService.deleteByIds(form.getIds());
        return R.ok();
    }
}
