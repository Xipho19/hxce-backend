package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchQuizResultForm {
    @NotBlank(message = "uuid不能为空")
    private String uuid;
}
