package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.hxce.api.controller.form.TestStudentForm;
import com.example.hxce.api.util.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping("/sayHello")
    @SaCheckLogin
    public R sayHello() {
        R r=R.ok(new HashMap<>(){{
            put("result","Hello World");
        }});
        return r;
    }
    @PostMapping("/sayHello2")
    public HashMap sayHello2(@RequestBody @Validated TestStudentForm form) {
        System.out.printf(form.getName());
        System.out.println(form.getPhone());
        return new HashMap(){
            {
                put("name","啦啦啦");
                put("phone","12345678965");
            }
        };
    }
}