package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class TestStudentForm {
    @NotEmpty(message="name不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$",message="name不正确")
    private String name;

    @NotEmpty(message="phone不能为空")
    @Pattern(regexp = "^1[34578][0-9]{9}$",message="phone不正确")
    private String phone;

}
