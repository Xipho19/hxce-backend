package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CheckRoleNameConflictForm {
    private Long id;

    @NotEmpty(message = "roleName不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5_\\-]{1,20}$", message = "roleName不正确")
    private String roleName;
}
