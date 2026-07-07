package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchRoleByPageForm {
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    @NotNull(message = "size不能为空")
    @Min(value = 1, message = "size不能小于1")
    private Integer size;

    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5_\\-]{1,20}$", message = "roleName不正确")
    private String roleName;

    private Integer status;
}
