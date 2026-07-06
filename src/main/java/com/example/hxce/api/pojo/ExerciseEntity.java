package com.example.hxce.api.pojo;

import com.mysql.cj.xdevapi.JsonArray;
import lombok.Data;

@Data
public class ExerciseEntity {
    private Long id;
    private Long userId;
    private String uuid;
    private String knowledge;
    private String exercise;
    private String difficulty;
    private Integer questionNum;
    private String createTime;
}
