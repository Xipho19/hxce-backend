package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchStudentByPageForm {
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Min(value = 1, message = "size不能小于1")
    private Integer size;

    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "name不正确")
    private String name;

    @Pattern(regexp = "^1[34578][0-9]{9}$", message = "phone不正确")
    private String phone;

    @Pattern(regexp = "^[01]$", message = "status不正确")
    private String status;
}
