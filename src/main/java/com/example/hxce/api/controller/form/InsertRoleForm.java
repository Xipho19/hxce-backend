package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class InsertRoleForm {
    @NotBlank(message = "roleName不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4e00-\\u9fa5]{2,10}$", message = "roleName内容不正确")
    private String roleName;

    @NotEmpty(message = "permissions不能为空")
    private Integer[] permissions;
    
    private String remark;
}
