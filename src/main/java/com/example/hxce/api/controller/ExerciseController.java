package com.example.hxce.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.json.JSONObject;
import com.example.hxce.api.controller.form.CreateExerciseForm;
import com.example.hxce.api.controller.form.SearchQuizResultForm;
import com.example.hxce.api.data.QuizResult;
import com.example.hxce.api.service.ExerciseService;
import com.example.hxce.api.util.R;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Resource
    private ExerciseService exerciseService;

    @PostMapping("/createExercise")
    @SaCheckPermission(value = {"ROOT","EXAM:INSERT"},mode= SaMode.OR)
    public R createExercise(@RequestBody @Validated CreateExerciseForm form) {
        long userId = StpUtil.getLoginIdAsLong();
        String uuid= IdUtil.simpleUUID();
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("uuid",uuid);
        map.putAll(cn.hutool.core.bean.BeanUtil.beanToMap(form));
        QuizResult result=exerciseService.create(map);

        map=new HashMap<>();
        map.put("content",result);
        //把考题的UUID生成二维码图片的Base64字符串
        QrConfig qrConfig=new QrConfig();
        qrConfig.setWidth(200);
        qrConfig.setHeight(200);
        String base64=QrCodeUtil.generateAsBase64(uuid,qrConfig,"png");
        map.put("base64",base64);
        return R.ok(map);


    }

    @PostMapping("/searchByUuid")
    @SaCheckLogin
    public R searchByUuid(@RequestBody @Validated SearchQuizResultForm form){
        JSONObject jsonObject=exerciseService.searchByUuid(form.getUuid());
        return R.ok(new HashMap<String,Object>(){{
            put("content",jsonObject);
        }});

    }
}
