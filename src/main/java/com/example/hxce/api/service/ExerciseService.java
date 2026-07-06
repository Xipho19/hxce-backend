package com.example.hxce.api.service;

import cn.hutool.json.JSONObject;
import com.example.hxce.api.data.QuizResult;
import io.lettuce.core.json.JsonObject;

import java.util.Map;

public interface ExerciseService {
    public QuizResult create(Map<String, Object> params);
    public JSONObject searchByUuid(String uuid);

}
