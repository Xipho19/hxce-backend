package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CreateExerciseForm {
    @NotBlank(message = "difficulty不能为空")
    @Pattern(regexp = "^(简单|中等|困难)$", message = "difficulty格式错误")
    private String difficulty;

    @NotNull(message = "questionNum不能为空")
    @Range(min = 1, max = 50, message = "questionNum必须在1到50之间")
    private Integer questionNum;

    @NotBlank(message = "knowledge不能为空")
    private String knowledge;
}
