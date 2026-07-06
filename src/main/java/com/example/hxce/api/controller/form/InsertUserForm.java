package com.example.hxce.api.controller.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class InsertUserForm {
    @NotBlank(message = "name不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,20}$", message = "name格式不正确")
    private String name;

    @NotBlank(message = "性别不能为空")
    @Pattern(regexp = "^男$|^女$", message = "性别不正确")
    private String gender;

    @NotBlank(message = "phone不能为空")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "phone格式不正确")
    private String phone;

    @NotEmpty(message = "roles不能为空")
    private Long[] roles;

    @NotNull(message = "status不能为空")
    @Range(min = 1, max = 3, message = "status必须为1~3之间的整数")
    private Byte status;
}
